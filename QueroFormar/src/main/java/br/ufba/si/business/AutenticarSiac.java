package br.ufba.si.business;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import br.ufba.si.entidade.Disciplina;
import br.ufba.si.entidade.Usuario;
import br.ufba.si.utils.ResultadoEnum;

/**
*
* @author Danilo Santos
*/

public class AutenticarSiac {
	
	private final DefaultHttpClient client = new DefaultHttpClient();


	   /**
	    * Efetua login no site
	    * @param url - URL de Login do site
	    * @param user - usuario
	    * @param password - senha
	    * @return true - login ok | false - login fail
	    * @throws UnsupportedEncodingException
	    * @throws IOException 
	    */
	   public boolean login(final String url, final String user, final String password) throws UnsupportedEncodingException, IOException {

	       /* Método POST */
	       final HttpPost post = new HttpPost(url);
	       boolean result = false;
	        
	       /* Configura os parâmetros do POST */
	       final List<NameValuePair> nameValuePairs =
	               new ArrayList<NameValuePair>();
	       nameValuePairs.add(new BasicNameValuePair("cpf", user));
	       nameValuePairs.add(new BasicNameValuePair("senha", password));
	       nameValuePairs.add(new BasicNameValuePair("x", "30"));
	       nameValuePairs.add(new BasicNameValuePair("y", "1"));
	        
	       /* 
	        * Codifica os parametros.
	        * 
	        */        
	       post.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));        
	           
	       /* Define navegador */
	       post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0");
	        
	       /* Efetua o POST */
	       HttpResponse response = client.execute(post);
	        
	       
	        
	       /* 
	        * Consome o conteúdo retornado pelo servidor
	        * Necessário esvaziar o response antes de usar o httpClient novamente
	        */
	       EntityUtils.consume(response.getEntity());

	       /* 
	        * Testar se o login funcionou.
	        * 
	        * Estratégia: acessar uma página que só está disponível quando se está logado
	        * Em caso de erro, o servidor irá redirecionar para a página de login
	        * A pagina de login contem uma string: "Login DevMedia"
	        * Se esta String estiver presente, significa que o login não foi efetuado com sucesso
	        * 
	        */
	       final HttpGet get = new HttpGet("https://siac.ufba.br/SiacWWW/ConsultarCoeficienteRendimento.do");
	       response = client.execute(get);        
	        
	       /*
	        * Verifica se a String: "ALUNO(A):" está presente
	        */
	       if (checkSuccess(response)) {
	           result = true;
	       } 
	       
	       return result;
	   }

	   /**
	    * Abre página
	    * @param url - Página a acessar
	    * @throws IOException 
	    */

		public void openPage(final String url, Usuario user) throws IOException {
	       final HttpGet get = new HttpGet(url);
	       final HttpResponse response = client.execute(get);
	       obterNomeMatricula(response, user);
	       final HttpResponse response2 = client.execute(get);
	       extrairHTLM(response2, user);	       

	   }

	   /**
	    * Encerra conexão
	    */
	   public void close() {
	       client.getConnectionManager().shutdown();
	   }
	    
	   /**
	    * Busca por String que indica se o usuário está logado ou não
	    * @param response
	    * @return true - Não achou String | false - Achou String
	    * @throws IOException 
	    */
	   private boolean checkSuccess(final HttpResponse response) throws IOException {
	       final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       String line;
	       boolean logado = false;
	            
	       /* Deixa correr todo o laco, mesmo achando a String, para consumir o content */
	       while ((line = rd.readLine()) != null) {
 
	           if(line.contains("<b>ALUNO(A):</b>")) {
	               logado = true;                
	           }
	       }
	       	       
	       return logado;
	   }
	    
	   /**
	    * Salva a página
	    * @param response
	    * @throws IOException 
	    */

	  /* private void saveHTLM(final HttpResponse response) throws IOException {
	       final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       String line;
	       File arquivo = new File("C:\\Users\\Danilo\\Desktop\\arquivo.html");
	       PrintWriter writer = new PrintWriter(arquivo);        
	       while ((line = rd.readLine()) != null) {
	           System.out.println(line);
	           writer.println(line); 
	       }        
	       writer.flush();
	       writer.close();

	   }*/

	   
	    /*
	     * Extrair Componentes
	     */
	   
	   private static void extrairHTLM(final HttpResponse response, Usuario user) throws IOException {

		   final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       String line;
	       boolean achei = false;
	       boolean stop = false;
	       int inicio, fim;
	       String corpoHistorico = "<table class=\"corpoHistorico\" cellspacing=\"0\" cellpadding=\"2\" width=\"95%\">";
	       String corpoFim = "Subtotal";
	                
	       Disciplina disciplina;
	       double nota; 
	       String natureza = "";
	       /* 
	        * Obtem Todas as materias cursadas pelo aluno 
	        * */
	       while ((line = rd.readLine()) != null && !stop) {
	    	   if(achei && line.contains("</td><td style=\"text-align: left;\">")) {

	    		   disciplina = new Disciplina();
	    		   natureza = "";
	    		   
	    		   //Obter Codigo Materia
	    		   inicio = line.indexOf("\">");
	    		   fim = line.indexOf("</td><td");
	    		   
	    		   disciplina.setCodigo(line.substring(inicio+2, fim));
	    		   
	    		   //Obter Nome
	    		   inicio = line.lastIndexOf("</td><td style=\"text-align: left;\">");
	    		   fim = line.indexOf("</td><td style=\"text-align: center;\">");
	    		   
	    		   disciplina.setNome(line.substring(inicio+35, fim).trim());
	    		   
	    		   //Obter CH
	    		   inicio = line.indexOf("</td><td style=\"text-align: center;\">");
	    		   fim = inicio+39;
	    		   
	    		   if(!"--".equals(line.substring(inicio+37, fim))){
	    			   Integer cargaHoraria = Integer.parseInt(line.substring(inicio+37, fim).trim());
		    		   disciplina.setCargaHoraria(cargaHoraria); 
	    		   }
	    		   
	    		   
	    		   //Obter Nota
	    		   fim = line.lastIndexOf("</td><td style=\"text-align: center;\">");
	    		   inicio = fim - 3;
	    		  
	    		   if(!">--".equals(line.substring(inicio, fim))){
	    			   disciplina.setNota(Double.parseDouble(line.substring(inicio, fim))); 
	    		   }
	    		   
	    		   //Obter Natureza
	    		   inicio = line.indexOf("center;\">O");
	    		   fim = inicio+11;
	    		   String nat = line.substring(inicio+9, fim);
	    		   
	    		   if("OB".equals(nat)){
	    			   disciplina.setNatureza("Obrigatória");
	    		   }else if("OP".equals(nat)){
	    			   disciplina.setNatureza("Optativa");
	    		   }
	    		   
	    		   //Obter NResultado	    		   	    		   
	    		   inicio = line.lastIndexOf("\">");
	    		   fim = line.indexOf("</td></tr>");
	    		   
	    		   disciplina.setResultado(ResultadoEnum.NãoIdentificado.comparaEnum(line.substring(inicio+2, fim)));
	    		   user.getMateriasCursadas().add(disciplina);
	           }else if(achei && line.contains("<td style=\"text-align: left;\"><b>")) {
	        	 //Obter Último Semestre Cursado
	    		   inicio = line.indexOf("<b>");
	    		   fim = line.indexOf("</b></td>");
	    		   user.setUltimoSemestreCursado(line.substring(inicio+3, fim));
	           }
	    	   
	    	   if(line.contains(corpoHistorico) && !achei) {
	        	   achei = true;                
	           }
	          
	           if(line.contains(corpoFim) & achei) {
	        	   stop = true;                
	           }
	       } 
	   }
	      
	   /* 
        * Obter Matricula e Nome do aluno 
        * */
	   private static void obterNomeMatricula(final HttpResponse response, Usuario user) throws IOException {
		   final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       String line;
	       boolean stop = false;
	       int inicio, fim;
	       String matricula = "<tr><td><b>MATRÍCULA:</b>";
	       String nome = "</td><td><b>NOME:</b> ";         
	       
	       while ((line = rd.readLine()) != null && !stop) {
	    	   if(line.contains(matricula)) {
	    		   inicio = line.indexOf(matricula);
	    		   fim = line.indexOf(nome);
	    		   //Matricula
	    		   user.setMatricula(line.substring(inicio+26, fim).trim());
	    		   
	    		   inicio = line.indexOf(nome);
	    		   fim = line.lastIndexOf("</td><td>&nbsp;</td></tr>");
	    		   //Nome
	    		   user.setNome(line.substring(inicio+22, fim).trim());	    		              
	    	   }
	       }
	   }
	   
	   
	   /**
	    * Roda aplicação
	    * @param args 
	    */
	   public static void main(String url, String cpf, String senha) {
		   
		   AutenticarSiac navegador = new AutenticarSiac();

	       try {
	           
	           boolean ok = navegador.login(url, cpf, senha);
	           if (ok) {
	               // Acessa página restrita
	               //navegador.openPage("https://siac.ufba.br/SiacWWW/ConsultarComponentesCurricularesCursados.do");

	           }
	           navegador.close();
	          
	       } catch (UnsupportedEncodingException ex) {
	           ex.printStackTrace();
	       } catch (IOException ex) {
	           ex.printStackTrace();
	       }
	   }
	}