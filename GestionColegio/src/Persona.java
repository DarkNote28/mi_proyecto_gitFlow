
public abstract class Persona {
private String dni;
private String apellido;
private String nombre;
private int edad;
public Persona(String dni, String apellido, String nombre, int edad) {
setDni(dni);
setApellido(apellido);
setNombre(nombre);
setEdad(edad);
}
// Getters y Setters con validaciones
public String getDni() {
return dni;
}
public void setDni(String dni) {
if (dni == null || !dni.matches("\\d{8}[A-Z]?")) {
throw new IllegalArgumentException("DNI inválido. Debe tener 8 dígitos u 8 dígitos + letra");
}
this.dni = dni;
}
public String getApellido() {
return apellido;
}
public void setApellido(String apellido) {
if (apellido == null || apellido.trim().isEmpty()) {
throw new IllegalArgumentException("El apellido no puede estar vacío");
}
this.apellido = apellido.trim();
}
public String getNombre() {
return nombre;
}
public void setNombre(String nombre) {
if (nombre == null || nombre.trim().isEmpty()) {
throw new IllegalArgumentException("El nombre no puede estar vacío");
}
this.nombre = nombre.trim();
}
public int getEdad() {
return edad;
}
public void setEdad(int edad) {
if (edad < 0 || edad > 120) {
throw new IllegalArgumentException("Edad inválida. Debe estar entre 0 y 120 años");
}
this.edad = edad;
}
public String getNombreCompleto() {
return apellido + ", " + nombre;
}
public abstract String getRol();
@Override
public String toString() {
return String.format("DNI: %s | Nombre: %s | Edad: %d años",
dni, getNombreCompleto(), edad);
}
}
