import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class GestionColegio {
private List<Alumno> alumnos;
public GestionColegio() {
this.alumnos = new ArrayList<>();
}
// Agregar alumno con manejo de errores
public boolean agregarAlumno(Alumno alumno) {
try {
if (alumno == null) {
throw new IllegalArgumentException("El alumno no puede ser nulo");
}
// Verificar si ya existe por DNI
if (existeAlumno(alumno.getDni())) {
throw new IllegalArgumentException("Ya existe un alumno con DNI " +
alumno.getDni());
}
alumnos.add(alumno);
return true;
} catch (IllegalArgumentException e) {
System.err.println(" Error al agregar: " + e.getMessage());
return false;
}
}
// Verificar si existe alumno por DNI
public boolean existeAlumno(String dni) {
return alumnos.stream().anyMatch(a -> a.getDni().equals(dni));
}
// Buscar por DNI
public Alumno buscarPorDni(String dni) {
if (dni == null || dni.trim().isEmpty()) {
throw new IllegalArgumentException("El DNI no puede estar vacío");
}
return alumnos.stream()
.filter(a -> a.getDni().equals(dni))
.findFirst()
.orElse(null);
}
// Buscar por apellido (pueden ser varios)
public List<Alumno> buscarPorApellido(String apellido) {
if (apellido == null || apellido.trim().isEmpty()) {
throw new IllegalArgumentException("El apellido no puede estar vacío");
}
return alumnos.stream()
.filter(a -> a.getApellido().toLowerCase().contains(apellido.toLowerCase()))
.collect(Collectors.toList());
}
// Buscar por nombre
public List<Alumno> buscarPorNombre(String nombre) {
if (nombre == null || nombre.trim().isEmpty()) {
throw new IllegalArgumentException("El nombre no puede estar vacío");
}
return alumnos.stream()
.filter(a -> a.getNombre().toLowerCase().contains(nombre.toLowerCase()))
.collect(Collectors.toList());
}
// Buscar por curso
public List<Alumno> buscarPorCurso(String curso) {
if (curso == null || curso.trim().isEmpty()) {
throw new IllegalArgumentException("El curso no puede estar vacío");
}
return alumnos.stream()
.filter(a -> a.getCurso().equalsIgnoreCase(curso))
.collect(Collectors.toList());
}
// Listar todos los alumnos
public List<Alumno> listarTodos() {
return new ArrayList<>(alumnos);
}
// Obtener cantidad de alumnos
public int getCantidadAlumnos() {
return alumnos.size();
}
// Eliminar alumno por DNI
public boolean eliminarAlumno(String dni) {
Alumno alumno = buscarPorDni(dni);
if (alumno != null) {
alumnos.remove(alumno);
return true;
}
return false;
}
// Actualizar promedio de un alumno
public boolean actualizarPromedio(String dni, double nuevoPromedio) {
Alumno alumno = buscarPorDni(dni);
if (alumno != null) {
try {
alumno.setPromedio(nuevoPromedio);
return true;
} catch (IllegalArgumentException e) {
System.err.println(" " + e.getMessage());
return false;
}
}
return false;
}
// Mostrar estadísticas
public void mostrarEstadisticas() {
if (alumnos.isEmpty()) {
System.out.println("No hay alumnos registrados.");
return;
}
double promedioGeneral = alumnos.stream()
.mapToDouble(Alumno::getPromedio)
.average()
.orElse(0);
long promocionados = alumnos.stream()
.filter(a -> a.getPromedio() >= 7)
.count();
long regulares = alumnos.stream()
.filter(a -> a.getPromedio() >= 4 && a.getPromedio() < 7)
.count();
long desaprobados = alumnos.stream()
.filter(a -> a.getPromedio() < 4)
.count();
System.out.println("\n========== ESTADÍSTICAS DEL COLEGIO ==========");
System.out.println("Total de alumnos: " + alumnos.size());
System.out.printf("Promedio general: %.2f%n", promedioGeneral);
System.out.println("Promocionados (≥7): " + promocionados);
System.out.println("Regulares (4-6.99): " + regulares);
System.out.println("Desaprobados (<4): " + desaprobados);
}
}