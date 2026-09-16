 public class Alumno extends Persona {
private String curso;
private int anioIngreso;
private double promedio;
public Alumno(String dni, String apellido, String nombre, int edad,
String curso, int anioIngreso) {
super(dni, apellido, nombre, edad);
setCurso(curso);
setAnioIngreso(anioIngreso);
this.promedio = 0.0;
}
// Getters y Setters
public String getCurso() {
return curso;
}
public void setCurso(String curso) {
if (curso == null || curso.trim().isEmpty()) {
throw new IllegalArgumentException("El curso no puede estar vacío");
}
this.curso = curso.trim();
}
public int getAnioIngreso() {
return anioIngreso;
}
public void setAnioIngreso(int anioIngreso) {
int anioActual = java.time.Year.now().getValue();
if (anioIngreso < 1900 || anioIngreso > anioActual) {
throw new IllegalArgumentException("Año de ingreso inválido (1900 - " + anioActual
+ ")");
}
this.anioIngreso = anioIngreso;
}
public double getPromedio() {
return promedio;
}
public void setPromedio(double promedio) {
if (promedio < 0 || promedio > 10) {
throw new IllegalArgumentException("El promedio debe estar entre 0 y 10");
}
this.promedio = promedio;
}
@Override
public String getRol() {
return "Alumno";
}
public String getEstadoAcademico() {
if (promedio >= 7) return "Promocionado";
if (promedio >= 4) return "Regular";
return "Desaprobado";
}
@Override
public String toString() {
return super.toString() + String.format(" | Curso: %s | Ingreso: %d | Promedio: %.2f | Estado: %s",
curso, anioIngreso, promedio, getEstadoAcademico());
}
 }

