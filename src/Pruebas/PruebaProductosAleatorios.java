package Pruebas

import Clases.Producto;
import Clases.TipoRopa;
import Clases.Marca;
import DataAcces.DaTipoRopa;
import DataAcces.DaMarca;
import DataAcces.DaProducto;
import Utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class PruebaProductosAleatorios {

    private DaMarca daMarca;
    private DaTipoRopa daTipoRopa;
    private DaProducto daProducto;

    public PruebaProductosAleatorios() throws SQLException {
        // Establecer conexión a la base de datos
        Connection connection = ConexionBD.getConnection();

        if (connection == null) {
            System.out.println("La conexión es nula. Verifica la configuración de la base de datos.");
            return;
        }

        this.daMarca = new DaMarca();
        this.daTipoRopa = new DaTipoRopa();
        this.daProducto = new DaProducto();
    }

    public static void main(String[] args) throws SQLException {
        PruebaProductosAleatorios prueba = new PruebaProductosAleatorios();
        prueba.insertarProductosAleatorios(100); // Cantidad de productos a insertar
    }

    public void insertarProductosAleatorios(int cantidadProductos) {
        String[] marcas = {
            "Supreme",
            "Stussy",
            "Rhude",
            "Palace",
            "Thrasher",
            "Bape",
            "Off-White",
            "Gosha Rubchinskiy",
            "Kith",
            "A Bathing Ape",
            "ASSC (Anti Social Social Club)",
            "Y-3",
            "Fear of God",
            "Acne Studios",
            "Carhartt",
            "Palm Angels",
            "Gucci",
            "Champion",
            "Vetements",
            "Heron Preston",
            "Noah",
            "424",
            "Undercover",
            "Cav Empt",
            "MISBHV",
            "Huf",
            "Obey",
            "Alyx",
            "Marcelo Burlon",
            "Maison Margiela"
        };

        String[] categorias = {
            "Camiseta",
            "Sudadera",
            "Pantalon",
            "Chaqueta",
            "Accesorio",
            "Gorra",
            "Zapatos",
            "Calcetines",
            "Bermuda",
            "Mochila",
            "Gafas de sol",
            "Reloj",
            "Bufanda",
            "Gorro",
            "Cinturon",
            "Ropa interior",
            "Pulsera",
            "Anillo",
            "Sombrero",
            "Chaleco"
        };

        String[] tallas = {"S", "M", "L", "XL"};
        String[] colores = {"Rojo", "Azul", "Negro", "Blanco", "Gris", "Verde", "Amarillo", "Rosa"};

        for (int i = 0; i < cantidadProductos; i++) {
            Producto nuevoProducto = new Producto();

            nuevoProducto.setId_producto(generarIdAleatorio());
            String nombreProducto = generarNombreProducto(marcas, categorias);
            String categoria = nombreProducto.split(" ")[0]; // Obtener la categoría
            String marca = nombreProducto.split(" ")[1]; // Obtener la marca
            nuevoProducto.setNombre(nombreProducto);
            nuevoProducto.setDescripcion(generarDescripcionProducto(categorias, tallas, categoria, marca));
            nuevoProducto.setPrecio_unitario(generarPrecioUnitario());
            nuevoProducto.setStock(generarStock());
            nuevoProducto.setFecha_creacion(convertirFecha(new Date(System.currentTimeMillis())));
            nuevoProducto.setFecha_actualizacion(convertirFecha(new Date(System.currentTimeMillis())));

            // Verificar y obtener el tipo de ropa
            TipoRopa tipoRopa = verificarTipoRopa(generarTipoRopa(categorias));
            if (tipoRopa != null) {
                nuevoProducto.setTipoRopa(tipoRopa);
            } else {
                System.out.println("Error al insertar el tipo de ropa.");
                continue; // Pasar al siguiente producto
            }

            // Verificar y obtener la marca
            Marca marcaObj = verificarMarca(generarMarca(marcas));
            if (marcaObj != null) {
                // Crear un nuevo objeto Marca con el ID correspondiente
                Marca nuevaMarca = new Marca();
                nuevaMarca.setId_marca(marcaObj.getId_marca());
                nuevaMarca.setNombre_marca(marcaObj.getNombre_marca());

                nuevoProducto.setMarca(nuevaMarca);
            } else {
                System.out.println("Error al insertar la marca.");
                continue; // Pasar al siguiente producto
            }

            nuevoProducto.setColor(generarColorAleatorio(colores));

            boolean productoAgregado = daProducto.agregar(nuevoProducto);

            if (productoAgregado) {
                System.out.println("Producto insertado: " + nuevoProducto.toString());
            } else {
                System.out.println("Error al insertar el producto: " + nuevoProducto.toString());
            }
        }
    }

    private int generarIdAleatorio() {
        return (int) (Math.random() * 1000); // Generar un ID aleatorio entre 0 y 999
    }

    private String generarNombreProducto(String[] marcas, String[] categorias) {
        Random random = new Random();
        int categoriaIndex = random.nextInt(categorias.length);
        String categoria = categorias[categoriaIndex];
        int marcaIndex = random.nextInt(marcas.length);
        String marca = marcas[marcaIndex];
        return categoria + " " + marca;
    }

    private String generarColorAleatorio(String[] colores) {
        Random random = new Random();
        int index = random.nextInt(colores.length);
        return colores[index];
    }

    private String generarDescripcionProducto(String[] categorias, String[] tallas, String categoria, String marca) {
        Random random = new Random();
        String talla = tallas[random.nextInt(tallas.length)];
        String descripcion = "";

        switch (categoria) {
            case "Camiseta":
                String[] descripcionesCamiseta = {
                    "Camiseta de manga corta",
                    "Camiseta estampada",
                    "Camiseta de algodón",
                    "Camiseta básica"
                };
                descripcion = talla + " " + descripcionesCamiseta[random.nextInt(descripcionesCamiseta.length)];
                break;
            case "Sudadera":
                String[] descripcionesSudadera = {
                    "Sudadera con capucha",
                    "Sudadera de cuello redondo",
                    "Sudadera estampada",
                    "Sudadera de felpa"
                };
                descripcion = talla + " " + descripcionesSudadera[random.nextInt(descripcionesSudadera.length)];
                break;
            case "Pantalon":
                String[] descripcionesPantalon = {
                    "Pantalón vaquero",
                    "Pantalón de chándal",
                    "Pantalón de tela",
                    "Pantalón cargo"
                };
                descripcion = talla + " " + descripcionesPantalon[random.nextInt(descripcionesPantalon.length)];
                break;
            case "Chaqueta":
                String[] descripcionesChaqueta = {
                    "Chaqueta bomber",
                    "Chaqueta de cuero",
                    "Chaqueta cortavientos",
                    "Chaqueta acolchada"
                };
                descripcion = talla + " " + descripcionesChaqueta[random.nextInt(descripcionesChaqueta.length)];
                break;
            case "Accesorio":
                String[] descripcionesAccesorio = {
                    "Gorra",
                    "Bufanda",
                    "Gafas de sol",
                    "Reloj",
                    "Cinturón"
                };
                descripcion = descripcionesAccesorio[random.nextInt(descripcionesAccesorio.length)];
                break;
            case "Gorra":
                String[] descripcionesGorra = {
                    "Gorra de béisbol",
                    "Gorra plana",
                    "Gorra de camionero"
                };
                descripcion = descripcionesGorra[random.nextInt(descripcionesGorra.length)];
                break;
            case "Zapatos":
                String[] descripcionesZapatos = {
                    "Zapatos casuales",
                    "Zapatos deportivos",
                    "Zapatos formales"
                };
                descripcion = descripcionesZapatos[random.nextInt(descripcionesZapatos.length)];
                break;
            case "Calcetines":
                String[] descripcionesCalcetines = {
                    "Calcetines cortos",
                    "Calcetines largos",
                    "Calcetines deportivos"
                };
                descripcion = descripcionesCalcetines[random.nextInt(descripcionesCalcetines.length)];
                break;
            case "Bermuda":
                String[] descripcionesBermuda = {
                    "Bermuda casual",
                    "Bermuda deportiva",
                    "Bermuda de playa"
                };
                descripcion = descripcionesBermuda[random.nextInt(descripcionesBermuda.length)];
                break;
            case "Mochila":
                String[] descripcionesMochila = {
                    "Mochila escolar",
                    "Mochila de viaje",
                    "Mochila deportiva"
                };
                descripcion = descripcionesMochila[random.nextInt(descripcionesMochila.length)];
                break;
            case "Gafas de sol":
                String[] descripcionesGafasSol = {
                    "Gafas de sol clásicas",
                    "Gafas de sol polarizadas",
                    "Gafas de sol deportivas"
                };
                descripcion = descripcionesGafasSol[random.nextInt(descripcionesGafasSol.length)];
                break;
            case "Reloj":
                String[] descripcionesReloj = {
                    "Reloj analógico",
                    "Reloj digital",
                    "Reloj deportivo"
                };
                descripcion = descripcionesReloj[random.nextInt(descripcionesReloj.length)];
                break;
            case "Bufanda":
                String[] descripcionesBufanda = {
                    "Bufanda de lana",
                    "Bufanda estampada",
                    "Bufanda tejida"
                };
                descripcion = descripcionesBufanda[random.nextInt(descripcionesBufanda.length)];
                break;
            case "Gorro":
                String[] descripcionesGorro = {
                    "Gorro de invierno",
                    "Gorro tejido",
                    "Gorro con pompón"
                };
                descripcion = descripcionesGorro[random.nextInt(descripcionesGorro.length)];
                break;
            case "Cinturon":
                String[] descripcionesCinturon = {
                    "Cinturon de cuero",
                    "Cinturon de tela",
                    "Cinturon trenzado"
                };
                descripcion = descripcionesCinturon[random.nextInt(descripcionesCinturon.length)];
                break;
            case "Ropa interior":
                String[] descripcionesRopaInterior = {
                    "Ropa interior para hombre",
                    "Ropa interior para mujer",
                    "Ropa interior de algodón"
                };
                descripcion = descripcionesRopaInterior[random.nextInt(descripcionesRopaInterior.length)];
                break;
            case "Pulsera":
                String[] descripcionesPulsera = {
                    "Pulsera de plata",
                    "Pulsera de cuero",
                    "Pulsera de perlas"
                };
                descripcion = descripcionesPulsera[random.nextInt(descripcionesPulsera.length)];
                break;
            case "Anillo":
                String[] descripcionesAnillo = {
                    "Anillo de street",
                    "Anillo de plata",
                    "Anillo de oro"
                };
                descripcion = descripcionesAnillo[random.nextInt(descripcionesAnillo.length)];
                break;
            case "Sombrero":
                String[] descripcionesSombrero = {
                    "Sombrero de paja",
                    "Sombrero de ala ancha",
                    "Sombrero de invierno"
                };
                descripcion = descripcionesSombrero[random.nextInt(descripcionesSombrero.length)];
                break;
            case "Chaleco":
                String[] descripcionesChaleco = {
                    "Chaleco acolchado",
                    "Chaleco de plumas",
                    "Chaleco de cuero"
                };
                descripcion = descripcionesChaleco[random.nextInt(descripcionesChaleco.length)];
                break;
            default:
                descripcion = "Producto " + generarPalabraAleatoria();
                break;
        }

        return descripcion;
    }

    private double generarPrecioUnitario() {
        return 19.99 + Math.random() * (99.99 - 19.99); // Generar un precio entre $19.99 y $99.99
    }

    private int generarStock() {
        return (int) (Math.random() * 100); // Generar un stock aleatorio entre 0 y 99
    }

    private String generarPalabraAleatoria() {
        String[] palabras = {
            "Skate", "Streetwear",
            "Hypebeast", "Sneakers", "Style", "Urban", "Fresh", "Dope", "Swagger", "Crew", "Under"
        };
        Random random = new Random();
        int palabraIndex = random.nextInt(palabras.length);
        return palabras[palabraIndex];
    }

    private String generarTipoRopa(String[] categorias) {
        Random random = new Random();
        int categoriaIndex = random.nextInt(categorias.length);
        return categorias[categoriaIndex];
    }

    private int indiceMarca = 0; // Variable de índice para marcas

    private String generarMarca(String[] marcas) {
        // Obtener la marca en el índice actual
        String marca = marcas[indiceMarca];

        // Incrementar el índice para la próxima llamada
        indiceMarca++;
        if (indiceMarca >= marcas.length) {
            indiceMarca = 0; // Reiniciar el índice si se alcanza el final del arreglo
        }

        return marca;
    }
// hago un casting para la fecha 

    private LocalDate convertirFecha(Date date) {
        return date.toLocalDate();
    }

    private TipoRopa verificarTipoRopa(String nombreTipoRopa) {
        List<TipoRopa> tiposRopa = daTipoRopa.obtenerTodos();
        for (TipoRopa tipoRopa : tiposRopa) {
            if (tipoRopa.getNombre_tipo().equals(nombreTipoRopa)) {
                return tipoRopa;
            }
        }
        // Si no existe, agregar el tipo de ropa a la base de datos
        TipoRopa nuevoTipoRopa = new TipoRopa();
        nuevoTipoRopa.setId_tipo(generarIdAleatorio());
        nuevoTipoRopa.setNombre_tipo(nombreTipoRopa);
        boolean tipoRopaAgregado = daTipoRopa.agregar(nuevoTipoRopa);
        if (tipoRopaAgregado) {
            return nuevoTipoRopa;
        } else {
            return null;
        }
    }

    private Marca verificarMarca(String nombreMarca) {
        List<Marca> marcas = daMarca.obtenerTodas();
        for (Marca marca : marcas) {
            if (marca.getNombre_marca().equals(nombreMarca)) {
                return marca;
            }
        }
        // Si no existe, agregar la marca a la base de datos
        Marca nuevaMarca = new Marca();
        nuevaMarca.setId_marca(generarIdAleatorio());
        nuevaMarca.setNombre_marca(nombreMarca);
        boolean marcaAgregada = daMarca.agregar(nuevaMarca);
        if (marcaAgregada) {
            return nuevaMarca;
        } else {
            return null;
        }
    }

}
