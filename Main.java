import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.themoviedb.org/3/discover/movie?include_adult=true&include_video=false&language=en-US&page=1&sort_by=popularity.desc"))
            .header("accept", "application/json")
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NjNiMzc4NGRiMTI3Y2UxYTA5MjE3YjRmZDljZjY1MSIsIm5iZiI6MTcyMzA4MjUyMi42MjQxNzYsInN1YiI6IjY2OTlkNTNiOGQwNWRkZGM0YTQ2MzVkNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9BPfAYgosiVTOux38MrjoSl--rcAY7CzO2gmFVRShp8")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String resposta = response.body();
       
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
       
        List<Filme> filmes = new ArrayList<>();

        




        Pattern tituloPattern = Pattern.compile("\"title\":\"([^\"]+)\"");
        Matcher tituloMatcher = tituloPattern.matcher(resposta);

        Pattern urlImagemPattern = Pattern.compile("\"poster_path\":\"([^\"]+)\"");
        Matcher urlImagemMatcher = urlImagemPattern.matcher(resposta);

        Pattern dataPattern = Pattern.compile("\"release_date\":\"([^\"]+)\"");
        Matcher dataMatcher = dataPattern.matcher(resposta);

        Pattern notaPattern = Pattern.compile("\"vote_average\":([\\d.]+)");
        Matcher notaMatcher = notaPattern.matcher(resposta);

        while (tituloMatcher.find() && urlImagemMatcher.find() && dataMatcher.find() && notaMatcher.find()) {
            String titulo = tituloMatcher.group(1);
            String urlImagem = urlImagemMatcher.group(1);
            String imagem = "https://image.tmdb.org/t/p/w500" + urlImagem;
            String dataString = dataMatcher.group(1);
            LocalDate data = LocalDate.parse(dataString, formato);
            
            String notaString = notaMatcher.group(1);
            Double nota = Double.parseDouble(notaString);

            
            
           

            filmes.add(new Filme(titulo, imagem, data, nota));

            System.out.println("Titulo: " + titulo);
            System.out.println("Imagem URL: " + imagem);
            System.out.println("Data: " + data);
            System.out.println("Nota: " + nota);
            System.out.println();
        }

        System.out.println(filmes.toString());

    }

    
}
