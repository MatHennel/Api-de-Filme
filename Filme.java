import java.time.LocalDate;

public class Filme {
    private String titulo;
    private String url;
    private LocalDate data;
    private Double nota;

    public Filme(String titulo, String url, LocalDate data, Double nota) {
        this.titulo = titulo;
        this.url = url;
        this.data = data;
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "\n Titulo: " + titulo + "\n" +
            "URL: " + url + "\n" +
            "Data: " + data + "\n" +
            "Nota: " + nota + "\n";
    }

}
