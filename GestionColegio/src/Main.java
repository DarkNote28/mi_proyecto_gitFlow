import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Main {
private static GestionColegio gestion = new GestionColegio();
private static Scanner scanner = new Scanner(System.in);
public static void main(String[] args) {
System.out.println("=========================================");
System.out.println(" SISTEMA DE GESTIÓN DE COLEGIO");
System.out.println("=========================================");
int opcion;
do {
mostrarMenu();
opcion = leerOpcion();
procesarOpcion(opcion);
} while (opcion != 0);
System.out.println("\n¡Gracias por usar el sistema!");
scanner.close();
}
private static void mostrarMenu() {
System.out.println("\n========== MENÚ PRINCIPAL ==========");
System.out.println("1. Registrar nuevo alumno");
System.out.println("2. Listar todos los alumnos");
System.out.println("3. Buscar por DNI");
System.out.println("4. Buscar por apellido");
System.out.println("5. Buscar por nombre");
System.out.println("6. Buscar por curso");
System.out.println("7. Actualizar promedio de alumno");
System.out.println("8. Eliminar alumno");
System.out.println("9. Ver estadísticas generales");
System.out.println("0. Salir");
System.out.print("➤ Elija una opción: ");
}
private static int leerOpcion() {
try {
return scanner.nextInt();
} catch (InputMismatchException e) {
scanner.next(); // limpiar buffer
return -1;
}
}
private static void procesarOpcion(int opcion) {
switch (opcion) {
case 1:
registrarAlumno();
break;
case 2:
listarAlumnos();
break;
case 3:
buscarPorDni();
break;
case 4:
buscarPorApellido();
break;
case 5:
buscarPorNombre();
break;
case 6:
buscarPorCurso();
break;
case 7:
actualizarPromedio();
break;
case 8:
eliminarAlumno();
break;
case 9:
gestion.mostrarEstadisticas();
break;
case 0:
System.out.println("Saliendo...");
break;
default:
System.out.println(" Opción inválida. Intente nuevamente.");
}
}
// ========== MÉTODOS DE REGISTRO CON VALIDACIONES ==========
private static void registrarAlumno() {
System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");
try {
scanner.nextLine(); // limpiar buffer
System.out.print("DNI (8 dígitos): ");
String dni = scanner.nextLine().trim();
System.out.print("Apellido: ");
String apellido = scanner.nextLine().trim();
System.out.print("Nombre: ");
String nombre = scanner.nextLine().trim();
System.out.print("Edad: ");
int edad = scanner.nextInt();
scanner.nextLine();
System.out.print("Curso (ej: 3° A, 4° B): ");
String curso = scanner.nextLine().trim();
System.out.print("Año de ingreso: ");
int anioIngreso = scanner.nextInt();
scanner.nextLine();
Alumno alumno = new Alumno(dni, apellido, nombre, edad, curso, anioIngreso);
// Opcional: pedir promedio inicial
System.out.print("Promedio inicial (0-10, opcional - Enter para 0): ");
String promedioInput = scanner.nextLine().trim();
if (!promedioInput.isEmpty()) {
try {
double promedio = Double.parseDouble(promedioInput);
alumno.setPromedio(promedio);
} catch (NumberFormatException e) {
System.out.println("Promedio inválido, se dejará en 0");
}
}
if (gestion.agregarAlumno(alumno)) {
System.out.println(" ¡Alumno registrado con éxito!");
}
} catch (IllegalArgumentException e) {
System.err.println(" Error en los datos: " + e.getMessage());
} catch (InputMismatchException e) {
System.err.println(" Error: Tipo de dato incorrecto");
scanner.nextLine(); // limpiar buffer
}
}
private static void listarAlumnos() {
List<Alumno> alumnos = gestion.listarTodos();
System.out.println("\n========== LISTADO COMPLETO DE ALUMNOS ==========");
if (alumnos.isEmpty()) {
System.out.println("No hay alumnos registrados.");
return;
}
System.out.printf("%-12s %-25s %-10s %-8s %-10s%n",
"DNI", "Nombre Completo", "Curso", "Promedio", "Estado");
System.out.println("----------------------------------------------------------------");
for (Alumno a : alumnos) {
System.out.printf("%-12s %-25s %-10s %-8.2f %-10s%n",
a.getDni(),
a.getNombreCompleto(),
a.getCurso(),
a.getPromedio(),
a.getEstadoAcademico());
}
System.out.println("Total: " + gestion.getCantidadAlumnos() + " alumnos");
}
private static void buscarPorDni() {
System.out.print("\nIngrese DNI a buscar: ");
String dni = scanner.next().trim();
try {
Alumno alumno = gestion.buscarPorDni(dni);
if (alumno != null) {
System.out.println("\n Alumno encontrado:");
System.out.println(alumno);
} else {
System.out.println(" No se encontró ningún alumno con DNI: " + dni);
}
} catch (IllegalArgumentException e) {
System.err.println(" " + e.getMessage());
}
}
private static void buscarPorApellido() {
System.out.print("\nIngrese apellido (o parte) a buscar: ");
String apellido = scanner.next().trim();
try {
List<Alumno> resultados = gestion.buscarPorApellido(apellido);
mostrarResultadosBusqueda(resultados, "apellido", apellido);
} catch (IllegalArgumentException e) {
System.err.println(" " + e.getMessage());
}
}
private static void buscarPorNombre() {
System.out.print("\nIngrese nombre (o parte) a buscar: ");
String nombre = scanner.next().trim();
try {
List<Alumno> resultados = gestion.buscarPorNombre(nombre);
mostrarResultadosBusqueda(resultados, "nombre", nombre);
} catch (IllegalArgumentException e) {
System.err.println(" " + e.getMessage());
}
}
private static void buscarPorCurso() {
scanner.nextLine(); // limpiar buffer
System.out.print("\nIngrese curso a buscar (ej: 3° A): ");
String curso = scanner.nextLine().trim();
try {
List<Alumno> resultados = gestion.buscarPorCurso(curso);
mostrarResultadosBusqueda(resultados, "curso", curso);
} catch (IllegalArgumentException e) {
System.err.println(" " + e.getMessage());
}
}
private static void mostrarResultadosBusqueda(List<Alumno> resultados, String tipo,
String valor) {
System.out.println("\n========== RESULTADOS DE BÚSQUEDA ==========");
System.out.println("Buscando por " + tipo + ": \"" + valor + "\"");
if (resultados.isEmpty()) {
System.out.println(" No se encontraron alumnos con ese " + tipo);
return;
}
System.out.printf("%-12s %-25s %-10s %-8s %-10s%n",
"DNI", "Nombre Completo", "Curso", "Promedio", "Estado");
System.out.println("----------------------------------------------------------------");
for (Alumno a : resultados) {
System.out.printf("%-12s %-25s %-10s %-8.2f %-10s%n",
a.getDni(),
a.getNombreCompleto(),
a.getCurso(),
a.getPromedio(),
a.getEstadoAcademico());
}
System.out.println("Coincidencias: " + resultados.size());
}
private static void actualizarPromedio() {
System.out.print("\nIngrese DNI del alumno: ");
String dni = scanner.next().trim();
Alumno alumno = gestion.buscarPorDni(dni);
if (alumno == null) {
System.out.println(" No se encontró alumno con DNI: " + dni);
return;
}
System.out.println("Alumno encontrado: " + alumno.getNombreCompleto());
System.out.print("Ingrese nuevo promedio (0-10): ");
try {
double nuevoPromedio = scanner.nextDouble();
if (gestion.actualizarPromedio(dni, nuevoPromedio)) {
System.out.println(" Promedio actualizado correctamente");
System.out.println("Nuevo estado: " + alumno.getEstadoAcademico());
}
} catch (InputMismatchException e) {
System.err.println(" Error: Debe ingresar un número válido");
scanner.next();
}
}
private static void eliminarAlumno() {
System.out.print("\nIngrese DNI del alumno a eliminar: ");
String dni = scanner.next().trim();
Alumno alumno = gestion.buscarPorDni(dni);
if (alumno == null) {
System.out.println(" No se encontró alumno con DNI: " + dni);
return;
}
System.out.println("Alumno a eliminar: " + alumno.getNombreCompleto());
System.out.print("¿Está seguro? (S/N): ");
String confirmacion = scanner.next().toUpperCase();
if (confirmacion.equals("S")) {
if (gestion.eliminarAlumno(dni)) {
System.out.println(" Alumno eliminado correctamente");
} else {
System.out.println(" Error al eliminar");
}
} else {
System.out.println("Eliminación cancelada");
}
}
}