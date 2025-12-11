# Light Challenge

*Aplicación Android con sensor de luz ambiental, puntajes, login y sincronización con API FastAPI*

---

##  Equipo de Desarrollo

- Diego Ali Salazar Maya  
- Jorge Noe Morales Cárdenas  
- Mauricio Tadeo Chagoyan Cortez  

---

## Descripción del Proyecto

*Light Challenge* es un juego interactivo para Android que aprovecha el *sensor de luz ambiental* del dispositivo.  
El objetivo es medir la intensidad de luz y convertirla en un puntaje que se incrementa conforme el usuario se expone a fuentes luminosas.  

La app integra:
- *SensorManager* para leer los valores del sensor de luz.  
- *Room Database* para almacenar usuarios y puntuaciones localmente.  
- *Retrofit + FastAPI (Python)* para enviar y consultar datos remotos.  
- *MVVM + Jetpack Compose* para una arquitectura moderna, clara y reactiva.

---

## Objetivo

El proyecto busca demostrar la integración entre hardware (sensor físico) y software (lógica de aplicación y backend).  
Además, ofrece un flujo completo de autenticación y almacenamiento persistente de puntajes.

---

## Tecnologías utilizadas

### Android (Frontend)
- *Kotlin*  
- *Jetpack Compose* (Interfaz declarativa)
- *Room* (Base de datos local)
- *Retrofit* (Comunicación HTTP)
- *Coroutines + ViewModel* (Arquitectura MVVM)
- *SensorManager* (Acceso al sensor de luz ambiental)
- *Material 3* (Diseño visual moderno)

### Python (Backend)
- *FastAPI* (Framework backend)
- *SQLite* (Base de datos del servidor)
- *Uvicorn* (Servidor local de desarrollo)

---

## Funcionalidades principales

| Función | Descripción |
|----------|--------------|
| *Detección de luz ambiental* | Usa el sensor del dispositivo para medir lux. |
| *Sistema de puntaje* | Aumenta el puntaje cada vez que el sensor detecta luz mayor a un umbral. |
| *Login y registro* | Permite crear cuentas e iniciar sesión. |
| *Almacenamiento local (Room)* | Guarda usuarios y puntuaciones incluso sin Internet. |
| *Sincronización remota (FastAPI)* | Envía los puntajes al servidor Python mediante Retrofit. |
| *Historial de puntuaciones* | Lista todas las partidas guardadas, permite editar o eliminar. |
| *Cierre de sesión seguro* | Limpia la sesión local y evita errores del sensor. |

---

## Sensor utilizado

### *Sensor de luz ambiental*
- Tipo: Sensor.TYPE_LIGHT
- Unidad: *Lux (lx)*
- Permiso requerido:  
  ```xml
  <uses-permission android:name="android.permission.BODY_SENSORS" />
