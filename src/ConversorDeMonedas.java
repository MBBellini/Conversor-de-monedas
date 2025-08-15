import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorDeMonedas {
    public static Moneda obtenerDatos(String api){
        Moneda moneda = null;

        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(api)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            moneda = gson.fromJson(response.body(), Moneda.class);
        } catch (Exception e){
            throw new RuntimeException("Moneda no encontrada");
        }
        return  moneda;
    }



    public static String cambiar(String tipoDeMoneda, String CambioDeMoneda, double valor){
        double resultado = 0;

        String api = "https://v6.exchangerate-api.com/v6/7c871081334d9c96ff074334/latest/" + tipoDeMoneda;
        Moneda moneda = obtenerDatos(api);
        double cambio = moneda.getConversion_rates().get(CambioDeMoneda);
        resultado = valor * cambio;

        return  "El valor de " + valor + " [" + tipoDeMoneda + "], es el valor final de " + resultado + " [" + CambioDeMoneda +"]\n";
    }

    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        String resultado = "";
        double valor = 0;

        while (opcion != 7){
        String Menu =
                """
                        **********************************
                        Bienvenido al sistema de conversor de monedas:
                        **********************************
                        Por favor, ingrese el valor que desae convertir
                        1) Dolar a Peso Argentino
                        2) Peso Argentino a Dolar
                        3) Dolar a Real Brasileño
                        4) Real Brasileño a Dolar
                        5) Euro a Dolar
                        6) Dolar a Euro
                        7) Salir del sistema
                        **********************************
                        Seleccione una opción válida
                """;

            System.out.println(Menu);
            opcion = scanner.nextInt();

            if (opcion != 7  && opcion <= 7){
                System.out.println("Ingrese el valor que desea convertir");
                valor = scanner.nextDouble();
            }

            switch (opcion){
                case 1:
                    resultado = ConversorDeMonedas.cambiar(TipoDeMoneda.USD.toString(),TipoDeMoneda.ARS.toString(), valor);
                    break;
                case 2:
                    resultado = ConversorDeMonedas.cambiar(TipoDeMoneda.ARS.toString(), TipoDeMoneda.USD.toString(), valor);
                    break;
                case 3:
                    resultado = ConversorDeMonedas.cambiar(TipoDeMoneda.USD.toString(),TipoDeMoneda.BRL.toString(), valor);
                    break;
                case 4:
                    resultado = ConversorDeMonedas.cambiar(TipoDeMoneda.BRL.toString(),TipoDeMoneda.USD.toString(), valor);
                    break;
                case 5:
                    resultado = ConversorDeMonedas.cambiar(TipoDeMoneda.EUR.toString(),TipoDeMoneda.USD.toString(), valor);
                    break;
                case 6:
                    resultado = ConversorDeMonedas.cambiar(TipoDeMoneda.USD.toString(),TipoDeMoneda.EUR.toString(), valor);
                    break;
                case 7:
                    System.out.println("Salir del sistema");
                    break;
                default:
                    System.out.println("Transacción inválida");
            }
            if(opcion != 7  && opcion <= 7 && !resultado.isEmpty())
                System.out.println(resultado);
        }
    }
}
