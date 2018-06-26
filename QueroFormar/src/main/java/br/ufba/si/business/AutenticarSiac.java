package br.ufba.si.business;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.ufba.si.entidade.Usuario;

/**
*
* @author marcelo
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
	        * Antes do encoder: fulano@email.com
	        * Depois do enconder: fulano%40email.com
	        */        
	       post.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));        
	           
	       /* Define navegador */
	       post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:18.0) Gecko/20100101 Firefox/18.0");
	        
	       /* Efetua o POST */
	       HttpResponse response = client.execute(post);
	        
	       /* Resposta HTTP: Sempre imprimirá “HTTP/1.1 302 Object moved” (no caso da devmedia) */
	       System.out.println("Login form get: " + response.getStatusLine());
	        
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
	        * Verifica se a String: "Login DevMedia" está presente
	        */
	       if (checkSuccess(response)) {
	           System.out.println("Conexao Estabelecida!");
	           result = true;
	       } else {
	           System.out.println("Login não-efetuado!");
	       }

	       return result;
	   }

	   /**
	    * Abre página
	    * @param url - Página a acessar
	    * @throws IOException 
	    */
	   public void openPage(final String url) throws IOException {
	       final HttpGet get = new HttpGet(url);
	       final HttpResponse response = client.execute(get);
	       saveHTLM(response);
	       final HttpResponse response2 = client.execute(get);
	       extrairHTLM(response2);
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
	       boolean found = false;
	       /* Deixa correr todo o laco, mesmo achando a String, para consumir o content */
	       while ((line = rd.readLine()) != null) {
	           if(line.contains("NÃO TENHO SENHA / ALTERAR SENHA")) {
	               found = true;                
	           }
	       }
	       return !found;
	   }
	    
	   /**
	    * Salva a página
	    * @param response
	    * @throws IOException 
	    */
	   private void saveHTLM(final HttpResponse response) throws IOException {
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
	   }
	   
	    /*
	     * Extrair Componentes
	     */
	   
	   private static void extrairHTLM(final HttpResponse response) throws IOException {
		   final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       String line;
	       boolean achei = false;
	       boolean stop = false;
	       int inicio, fim;
	       String corpoHistorico = "<table class=\"corpoHistorico\" cellspacing=\"0\" cellpadding=\"2\" width=\"95%\">";
	       String corpoFim = "Subtotal";
	             
	       File componente = new File("C:\\Users\\Danilo\\Desktop\\componente.txt");
	       PrintWriter writer = new PrintWriter(componente);        
	         
	       /* 
	        * Obtem Todas as materias cursadas pelo aluno 
	        * */
	       while ((line = rd.readLine()) != null && !stop) {
	    	   if(achei && line.contains("</td><td style=\"text-align: left;\">")) {
	    		   inicio = line.indexOf("\">");
	    		   fim = line.indexOf("</td><td");
	    		   String materia = line.substring(inicio+2, fim);
	    		   
	    		   fim = line.lastIndexOf("</td><td style=\"text-align: center;\">");
	    		   inicio = fim - 3;
	    		   
	    		   materia = materia+"   "+line.substring(inicio, fim);
	    		   
	    		   inicio = line.lastIndexOf("\">");
	    		   fim = line.indexOf("</td></tr>");
	    		   materia = materia+"   "+line.substring(inicio+2, fim);
	    		   writer.println(materia);               
	           }
	    	   
	    	   if(line.contains(corpoHistorico) && !achei) {
	        	   achei = true;                
	           }
	          
	           if(line.contains(corpoFim) & achei) {
	        	   stop = true;                
	           }
	           
	       } 
	       writer.flush();
	       writer.close();
	   }
	   
	   /**
	    * Extrair Dados do aluno para alicação
	    * @param url - Página a acessar
	    * @throws IOException 
	    */
	   public void extrairDados(final String url, Usuario user) throws IOException {
	       final HttpGet get = new HttpGet(url);
	       final HttpResponse response = client.execute(get);
	       obterNomeMatricula(response, user);
	   }
	   
	   private static void obterNomeMatricula(final HttpResponse response, Usuario user) throws IOException {
		   final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       String line;
	       boolean stop = false;
	       int inicio, fim;
	       String matricula = "<tr><td><b>MATRÍCULA:</b>";
	       String nome = "</td><td><b>NOME:</b> ";
	             
	                
	       /* 
	        * Obter Matricula e Nome do aluno 
	        * */
	       while ((line = rd.readLine()) != null && !stop) {
	    	   if(line.contains(matricula)) {
	    		   inicio = line.indexOf(matricula);
	    		   fim = line.indexOf(nome);
	    		   //Matricula
	    		   user.setMatricula(line.substring(inicio+26, fim));
	    		   
	    		   inicio = line.indexOf(nome);
	    		   fim = line.lastIndexOf("</td><td style=\"text-align: center;\">");
	    		   
	    		   user.setNome(line.substring(inicio+22, fim));
	    		   
	    		   stop = true; 
	    		              
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
	           // Tenta efetuar login
	    	   // "05416065575", "NW62LFAB");
	    	   //"02278164554", "adrianolucas");
	           boolean ok = navegador.login(url, cpf, senha);
	           if (ok) {
	               // Acessa página restrita
	               navegador.openPage("https://siac.ufba.br/SiacWWW/ConsultarComponentesCurricularesCursados.do");
	           }
	           navegador.close();
	          
	       } catch (UnsupportedEncodingException ex) {
	           ex.printStackTrace();
	       } catch (IOException ex) {
	           ex.printStackTrace();
	       }
	   }
	}