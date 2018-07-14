
public class Main {

    public static void main(String args[]) {

        Curso curso = new Curso();
        curso.setNome("Farmácia");
        curso.setHoras(3600);
        curso.setTurno(Turno.MANHA);


//        System.out.println("Curso " + curso.getNome()
//                + " disponível no turno da "
//                + curso.getTurno().getDescricao());

        for (Turno t : Turno.values()) {
            System.out.println(t.getDescricao());
        }
    }
}
