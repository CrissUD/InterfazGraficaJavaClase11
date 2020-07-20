# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 11

## Objetivos

* Examinar las características principales del objeto gráfico JTable y como podemos crearla, y gestionarla para nuestras interfaces gráficas.
* Reconocer el propósito del uso de tablas dentro de nuestras interfaces y como gestionar la información a través de estas.
* Identificar las acciónes principales de gestión de información en una tabla tal como insertar información, modificarla, filtrarla y eliminarla.
* Personalizar la tabla con estilos para que esta pueda tener un aspecto conforme con el resto de la interfaz gráfica.

# Antes de Comenzar

Dentro de nuestro paquete **archives** vamos a agregar otro archivo plano llamado **amigos.txt** que contendrá información general de los contactos que vamos a gestionar. Este archivo planos lo puede encontrar en este mismo repositorio entrando a la carpeta **Clase11** seguido de la carpeta **src** y luego en la carpeta **archives**.

<div align='center'>
    <img  src='https://i.imgur.com/qQxza9B.png'>
    <p>Se inserta archivo plano dentro del paquete archives.</p>
</div>

Vamos a crear un borde lineal de color azul en nuestro servicio **RecursosService**.

* **Declaración:**
```javascript
private Border borderAzul;
```

* **Ejemplificación**
```javascript
// Dentro del constructor
borderAzul = BorderFactory.createLineBorder(colorAzul, 2, true);
```

* **Método get**
```javascript
public Border getBorderAzul(){
    return borderAzul;
}
```

Recordando un poco nuestro recorrido, hemos utilizado los servicios para gestionar varias cosas dentro de nuestro proyecto, una de estas fue la generación automática de la reutilización del componente **Accion** a traves de la obtención de la información externa. Por otra parte ahora gestionamos **el ingreso de nuestra aplicación** a traves de algunos usuarios que han sido registrados y están contenidos de forma persistente (archivo plano) y gracias a los servicios no solo podemos controlar el ingreso único de estos usuarios sino que ademas podemos gestionar la información de estos a traves de varias partes de nuestro proyecto de forma independiente.

# Creación y gestión de tablas

En esta sesión vamos a ver la creación y gestión del objeto gráfico avanzado **JTable** el cual es muy util para gestionar información, para dar un recorrido completo al uso de este objeto vamos a ver ciertos items como:

* Creación de Servicios y preparativos para el uso de JTable.
* Creación de JTable y sus partes.
* Gestión de información dentro de un JTable.
* Personalización del JTable.

# Creación de servicios y preparativos para el uso de JTable.

En esta lección vamos a construir el componente gráficos **Amigos** con el cual un usuario podrá gestionar información de sus contactos, para esto vamos a necesitar traer información externa la cual contiene los datos de cada uno de los contactos. Vamos a ver la estructura del archivo plano.

<div align='center'>
    <img  src='https://i.imgur.com/cyoj18I.png'>
    <p>Archivo plano con información de los contactos</p>
</div>

Podemos ver que los datos de un contacto (Amigo) son:
* **Id**.
* **Nombre:**.
* **Edad**.
* **Oficio**.
* **Numero Telefónico**.
* **Email**.

## Preparación de Servicio y clases para gestionar la información externa

Igual que en la sesión anterior, vamos a crear una clase que representará en objetos la información de un amigo, esta clase la creamos dentro del paquete **models**.

<div align='center'>
    <img  src='https://i.imgur.com/FEagzsq.png'>
    <p>Creación de la clase amigo.</p>
</div>

Vamos a crear los atributos necesarios para representar esta información y sus respectivos métodos **set y get**.
```javascript
public class Amigo {
    private int id;
    private String nombre, edad, oficio, telefono, email;

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getEdad(){
        return edad;
    }

    public String getOficio(){
        return oficio;
    }

    public String getTelefono(){
        return telefono;
    }

    public String getEmail(){
        return email;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEdad(String edad){
        this.edad = edad;
    }

    public void setOficio(String oficio){
        this.oficio = oficio;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
```

Como suponemos que esta información la vamos a recibir desde una fuente foránea vamos a crear un controlador externo que se encarga de gestionar esta información y con la cual nuestra aplicación va a interactuar.

<div align='center'>
    <img  src='https://i.imgur.com/tyMmpt6.png'>
    <p>Creación de clase ControlAmigos en el paquete logic</p>
</div>

Igual que en la sesión anterior como esta clase sera la que gestiona la información, va a contener un atributo en forma de arreglo que contenga los objetos de los amigos:

* **Declaración:**
```javascript
private ArrayList<Amigo> amigos;
```

* **Ejemplificación:**
```javascript
public ControlAmigos(){
    amigos = new ArrayList<Amigo>();
}
```

Vamos a escribir el método que se encarga de recibir la información externa desde el archivo plano y lo vamos a llamar desde el constructor:

```javascript
public void cargarDatos(){
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    try {
        archivo = new File ("Clase11/src/archives/amigos.txt");
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);
        String linea;
        while((linea=br.readLine())!=null){
            String[] atributos = linea.split(",");
            Amigo amigo = new Amigo();
            amigo.setId(Integer.parseInt(atributos[0]));
            amigo.setNombre(atributos[1]);
            amigo.setEdad(atributos[2]);
            amigo.setOficio(atributos[3]);
            amigo.setTelefono(atributos[4]);
            amigo.setEmail(atributos[5]);
            amigos.add(amigo);
        }
        fr.close(); 
    }
    catch(Exception e){
        e.printStackTrace();
    }
}
```

```javascript
// Dentro del Costructor
cargarDatos();
```

Finalmente vamos a crear un método con el cual se devolverá todo el arreglo, en este caso a diferencia del ejemplo de la lección pasada el controlador solo devolverá la información que obtuvo en forma del arreglo y en el servicio vamos a manipular este arreglo:

```javascript
public ArrayList<Amigo> getAmigos(){
    return amigos;
}
```

Es hora de crear nuestro Servicio este sera el servicio **AmigoService** y estará dentro del paquéte **ServicesLogic**:

<div align='center'>
    <img  src='https://i.imgur.com/WVRzz2r.png'>
    <p>Creación del servicio dentro del paquete ServicesLogic</p>
</div>

Como ya vimos a lo largo del curso vamos a crear el mecanismo para una ejemplificación única de nuestro servicio dejando nuestro constructor publica para dar posibilidad a la creación de mas ejemplificaciones en caso de ser necesario:

* **Declaración referencia estática de si mismo:**
```javascript
private static AmigoService servicio;
```

* **Método estático para ejemplificación única:**
```javascript
public static AmigoService getService(){
    if(servicio == null)
        servicio = new AmigoService();
    return servicio;
}
```

En este servicio vamos a realizar la manipulación de la información externa, por lo que vamos a crear un objeto que referencia al control externo y ademas un objeto contenedor del arreglo de amigos que sera igual al que reciba desde el controlador:

* **Declaración:**
```javascript
private ControlAmigos cAmigos;
private ArrayList<Amigo> amigos;
```

* **Ejemplificación:**
```javascript
public AmigoService(){
    cAmigos = new ControlAmigos();
    amigos = cAmigos.getAmigos();
}
```
Se puede notar que dentro del constructor estamos llamando al método **getAmigos** que nos devolverá el arreglo y lo igualamos al arreglo antes declarado.

Como vamos a manipular la información de los amigos desde este servicio vamos a crear 3 métodos que nos serán útiles, entre sus funcionalidades queremos:

* **Obtener un objeto de un Amigo a traves de su posición en el arreglo:**
```javascript
public Amigo devolverAmigo(int posicion){
    try{
        return amigos.get(posicion);
    }
    catch(Exception e){
        return null;
    }
}
```

* **Agregar un nuevo Amigo al arreglo de objetos:**
```javascript
public void agregarAmigo(Amigo amigo){
    this.amigos.add(amigo);
}
```

* **Devolver la cantidad de amigos que hay en el arreglo:**
```javascript
public int devolverCantidadAmigos(){
    return amigos.size();
}
```

***Nota:** Debido a que solo nos estamos concentrando en la parte Frontend del proyecto no vamos a gestionar la información más allá de propósitos de la clase, por lo que la información nueva que creemos no se guardara de forma persistente.*

## Preparación del Componente Gráfico Amigos

Ya tenemos lista toda la parte del manejo de la información externa dentro de nuestro proyecto ahora vamos a construir nuestro componente Gráfico **Amigos** y asi tener todo listo para el manejo de **JTable** dentro de el. Lo primero que vamos a realizar es la configuración necesaria dentro de nuestra clase **AmigosComponent**, dentro de este componente vamos a gestionar algunos eventos por lo que vamos a implementar las interfaces que necesitemos:

```javascript
public class AmigosComponent implements ActionListener, MouseListener, FocusListener {
    . . .
    . . .
}
```

Se puede notar que nuestro componente va a implementar 3 interfaces, **ActionListener**, **MouseListener** y **FocusListener**. Hasta el momento hemos revisado las dos primeras interfaces, la tercera la estamos usando por primera vez. Esta interfaz va a escuchar eventos cada vez que un elemento que escuche a este tipo de eventos sea seleccionado, cuando implementemos este tipo de eventos se hará una mayor explicación, por ahora vamos a implementar todos los métodos correspondientes a cada interfaz:

```javascript
// MÉTODO IMPLEMENTADO DE ACTIONLISTENER
@Override
public void actionPerformed(ActionEvent e) {}

// MÉTODOS IMPLEMENTADO DE FOCUSLISTENER
@Override
public void focusGained(FocusEvent e) {}

@Override
public void focusLost(FocusEvent e) {}

// MÉTODOS IMPLEMENTADO DE MOUSELISTENER
@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mousePressed(MouseEvent e) {}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}
```

Vamos a realizar el llamado al servicio **AmigoService** para poder traer la información externa más adelante:

* **Declaración**:
```javascript
private AmigoService sAmigos;
```

* **Ejemplificación**:
```javascript
public AmigosComponent() {
    sAmigos = AmigoService.getService();
    ...
}
```

Ademas de esto vamos a declarar dos atributos que nos serán útiles para algunas cosas que realizaremos mas adelante, el primero sera un objeto de tipo **Amigo**(Modelo) el segundo sera un arreglo de Strings que contiene todos los *placeholders* de nuestros JTextFields:
```javascript
private Amigo amigo;
private String[] placeholdes = {"Nombre","Edad","Oficio","Telefono","Email","Filtrar..."};
```

Ahora vamos a concentrarnos en la clase **AmigosTemplate**, primero realizaremos la llamada de los serviciós gráficos necesarios:

* **Declaración:**
```javascript
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos;
```

* **Obtención de servicios:**
```javascript
// Dentro del constructor
this.sObjGraficos = ObjGraficosService.getService();
this.sRecursos = RecursosService.getService();
```

Vamos a quitar el background de prueba que tenia el componente y vamos a cambiarlo por el color gris claro que llamaremos desde el servicio **recursosService**:
```javascript
// Dentro del constructor
this.setBackground(sRecursos.getColorGrisClaro());
```

Vamos a crear ademas un objeto decorador que sera un color gris con una tonalidad un poco mas oscura, lo vamos a crear dentro del componente gráfico ya que solo se usara por el momento aquí y realizaremos su ejemplificación dentro del constructor ya que solo crearemos un objeto decorador.

* **Declaración:**
```javascript
private Color colorGris;
```

* **Ejemplificación:**
```javascript
// Dentro del constructor
this.colorGris = new Color(235,235,235);
```

Vamos a empezar a crear los objetos gráficos necesarios. Para empezar vamos a necesitar 2 paneles:
* **pOpciones**: El cual contendrá varias opciones que el usuario tendrá disponible para manipular la información de la tabla.
* **pDatos**: El cual contendrá información de cada contacto y que también podrá editar el usuario.

* **Declaración:**
```javascript
private JPanel pOpciones, pDatos;
```

* **Método encargado de la creación:**

```javascript
public void crearJPanels(){
    pOpciones = sObjGraficos.construirJPanel(10, 10, 580, 200, Color.WHITE, null);
    this.add(pOpciones);

    pDatos = sObjGraficos.construirJPanel(600, 10, 240, 580, Color.WHITE, null);
    this.add(pDatos);
}
```

* **Llamada del método en el constructor:**
```javascript
// Dentro del constructor
this.crearJPanels();
```

Nuestra aplicación se vera algo asi:
<div align='center'>
    <img  src='https://i.imgur.com/JRovJf9.png'>
    <p>Maquetación de los paneles dentro del componente Amigos</p>
</div>

Vamos a crear el contenido del panel **pOpciones**, este va a estar conformado por una serie de botones y un JTextField, los botones serán el medio para que el usuario interactué con la tabla mientras que el JTextField sera un espacio para que el usuario pueda filtrar y buscar información dentro de la tabla.

* **Declaración Objetos Gráficos:**
```javascript
private JButton bMostrar, bInsertar, bFiltrar, bModificar, bEliminar;
private JTextField tConsulta;
```

* **Método encargado de crear el contenido del panel pOpciones**:
```javascript
public void crearContenidoPOpciones(){
    // LABEL TITULO--------------------------------------------------------------------
    lTitulo = sObjGraficos.construirJLabel(
        "Edición de Contactos", 20, 10, 200, 30, null, sRecursos.getColorGrisOscuro(), 
        null, sRecursos.getFontTitulo(), "c"
    );
    pOpciones.add(lTitulo);

    // TEXTFIELD CONSULTA--------------------------------------------------------------------
    tConsulta = sObjGraficos.construirJTextField(
        "Filtrar...", 30, 60, 380, 40, colorGris , sRecursos.getColorGrisOscuro(), 
        sRecursos.getColorGrisOscuro(), sRecursos.getFontBotones(), null, "c"
    );
    tConsulta.addFocusListener(amigosComponent);
    pOpciones.add(tConsulta);

    // BOTÓN FILTRAR--------------------------------------------------------------------
    bFiltrar = sObjGraficos.construirJButton(
        "Filtrar", 430, 65, 120, 35, sRecursos.getCMano(), null, sRecursos.getFontBotones(), 
        sRecursos.getColorAzul(), Color.WHITE, null, "c", true
    );
    bFiltrar.addMouseListener(amigosComponent);
    bFiltrar.addActionListener(amigosComponent);
    pOpciones.add(bFiltrar);

    // BOTÓN MOSTRAR--------------------------------------------------------------------
    bMostrar = sObjGraficos.construirJButton(
        "Mostrar", 20, 145, 120, 35, sRecursos.getCMano(), null, sRecursos.getFontBotones(), 
        sRecursos.getColorAzul(), Color.WHITE, null, "c", true
    );
    bMostrar.addMouseListener(amigosComponent);
    bMostrar.addActionListener(amigosComponent);
    pOpciones.add(bMostrar);

    // BOTÓN INSERTAR--------------------------------------------------------------------
    bInsertar = sObjGraficos.construirJButton(
        "Insertar", 160, 145, 120, 35, sRecursos.getCMano(), null, sRecursos.getFontBotones(), 
        sRecursos.getColorAzul(), Color.WHITE, null, "c", true
    );
    bInsertar.addMouseListener(amigosComponent);
    bInsertar.addActionListener(amigosComponent);
    pOpciones.add(bInsertar);

    // BOTÓN MODIFICAR--------------------------------------------------------------------
    bModificar = sObjGraficos.construirJButton(
        "Modificar", 300, 145, 120, 35, sRecursos.getCMano(), null, sRecursos.getFontBotones(), 
        sRecursos.getColorAzul(), Color.WHITE, null, "c", true
    );
    bModificar.addMouseListener(amigosComponent);
    bModificar.addActionListener(amigosComponent);
    pOpciones.add(bModificar);

    // BOTÓN ELIMINAR--------------------------------------------------------------------
    bEliminar= sObjGraficos.construirJButton(
        "Eliminar", 440, 145, 120, 35, sRecursos.getCMano(), null, sRecursos.getFontBotones(), 
        sRecursos.getColorAzul(), Color.WHITE, null, "c", true
    );
    bEliminar.addMouseListener(amigosComponent);
    bEliminar.addActionListener(amigosComponent);
    pOpciones.add(bEliminar);
}
```

Se puede observar que dentro de la construcción de cada botón estamos añadiendo de una vez la propiedad de escucha a los eventos de acción **ActionListener** y los eventos del mouse **MouseListener**. La primera interfaz se usara para ejecutar las acciónes de manipulación de la información mientras que la segunda sera para roles mas estéticos. Por otro lado el JTextField adiciona la escucha a los eventos **FocusListener**.

* **Llamada del método en el constructor:**
```javascript
this.crearContenidoPOpciones();
```

Nuestra interfaz se vera algo así:

<div align='center'>
    <img  src='https://i.imgur.com/Bhv2UV4.png'>
    <p>Contenido integrado del panel pOpciones</p>
</div>

Ahora vamos a crear el contenido del panel **pDatos**, en este habrá una serie de JLabes y JTextField donde el usuario podrá editar la información de algún amigo:

* **Declaración:**
```javascript
private JLabel lTitulo, lInstrucciones, lEslogan;
private JLabel lId, lIdValor, lNombre, lEdad, lOficio, lTelefono, lEmail;
private JTextField tNombre, tEdad, tOficio, tTelefono, tEmail;
```

* **Método encargado de crear el contenido del panel pDatos:**

```javascript
public void crearContenidoPDatos(){
    // LABEL INSTRUCCIONES ----------------------------------------------------------------
    lInstrucciones = sObjGraficos.construirJLabel(
        "<html>Datos de los contactos<html>", 20, 10, 120, 50, null, 
        sRecursos.getColorGrisOscuro(), null, sRecursos.getFontTitulo(), "l"
    );
    pDatos.add(lInstrucciones);

    // LABEL ESLOGAN ----------------------------------------------------------------
    lEslogan = sObjGraficos.construirJLabel(
        "<html>A continuación puede ver y editar la información del Contacto<html>", 
        20, 50, 180, 90, null, sRecursos.getColorGrisOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lEslogan);

    // LABEL ID ----------------------------------------------------------------
    lId = sObjGraficos.construirJLabel(
        "Id Contacto:", 20, 140, 160, 30, null, 
        sRecursos.getColorAzulOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lId);

    // LABEL ID CONTENIDO ----------------------------------------------------------
    lIdValor = sObjGraficos.construirJLabel(
        "0", 120, 140, 160, 30, null, sRecursos.getColorAzulOscuro(), 
        null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lIdValor);

    // LABEL NOMBRE ----------------------------------------------------------------
    lNombre = sObjGraficos.construirJLabel(
        "Nombre Contacto:", 20, 180, 160, 30, null, 
        sRecursos.getColorAzulOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lNombre);

    // TEXTFIELD NOMBRE ----------------------------------------------------------------
    tNombre = sObjGraficos.construirJTextField(
        "Nombre", 30, 215, 180, 30, colorGris, sRecursos.getColorGrisOscuro(),
        sRecursos.getColorGrisOscuro(), sRecursos.getFontPequeña(), null, "c"
    );
    tNombre.addFocusListener(amigosComponent);
    pDatos.add(tNombre);

    // LABEL EDAD ----------------------------------------------------------------
    lEdad = sObjGraficos.construirJLabel(
        "Edad Contacto:", 20, 265, 160, 30, null, 
        sRecursos.getColorAzulOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lEdad);

    // TEXTFIELD NOMBRE ----------------------------------------------------------------
    tEdad = sObjGraficos.construirJTextField(
        "Edad", 30, 300, 180, 30, colorGris, sRecursos.getColorGrisOscuro(),
        sRecursos.getColorGrisOscuro(), sRecursos.getFontPequeña(), null, "c"
    );
    tEdad.addFocusListener(amigosComponent);
    pDatos.add(tEdad);
    
    // LABEL OFICIO ----------------------------------------------------------------
    lOficio = sObjGraficos.construirJLabel(
        "Oficio Contacto:", 20, 350, 160, 30, null, 
        sRecursos.getColorAzulOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lOficio);

    // TEXTFIELD OFICIO ----------------------------------------------------------------
    tOficio = sObjGraficos.construirJTextField(
        "Oficio", 30, 385, 180, 30, colorGris, sRecursos.getColorGrisOscuro(),
        sRecursos.getColorGrisOscuro(), sRecursos.getFontPequeña(), null, "c"
    );
    tOficio.addFocusListener(amigosComponent);
    pDatos.add(tOficio);
    
    // LABEL TELEFONO ----------------------------------------------------------------
    lTelefono = sObjGraficos.construirJLabel(
        "Telefono Contacto:", 20, 425, 160, 30, null, 
        sRecursos.getColorAzulOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lTelefono);

    // TEXTFIELD TELEFONO ----------------------------------------------------------------
    tTelefono = sObjGraficos.construirJTextField(
        "Telefono", 30, 460, 180, 30, colorGris, sRecursos.getColorGrisOscuro(),
        sRecursos.getColorGrisOscuro(), sRecursos.getFontPequeña(), null, "c"
    );
    tTelefono.addFocusListener(amigosComponent);
    pDatos.add(tTelefono);

    // LABEL EMAIL ----------------------------------------------------------------
    lEmail = sObjGraficos.construirJLabel(
        "Email Contacto:", 20, 510, 160, 30, null, 
        sRecursos.getColorAzulOscuro(), null, sRecursos.getFontPequeña(), "l"
    );
    pDatos.add(lEmail);

    // TEXTFIELD EMAIL ----------------------------------------------------------------
    tEmail = sObjGraficos.construirJTextField(
        "Email", 30, 545, 180, 30, colorGris, sRecursos.getColorGrisOscuro(),
        sRecursos.getColorGrisOscuro(), sRecursos.getFontPequeña(), null, "c"
    );
    tEmail.addFocusListener(amigosComponent);
    pDatos.add(tEmail);
}
```
Podemos notar que los JTextFields adicionan la escucha a los eventos **FocusListener**.

* **Llamada del método dentro del constructor:**
```javascript
// Dentro del Constructor
this.crearContenidoPDatos();
```

Nuestra aplicación se ve de la siguiente manera:
<div align='center'>
    <img  src='https://i.imgur.com/GaokBcw.png'>
    <p>Incorporación de contenido del panel pDatos.</p>
</div>

ahora vamos a crear los métodos **get** de los objetos gráficos que necesitaremos en la clase **AmigosComponent**:
```javascript
public JButton getBMostrar(){
    return bMostrar;
}

public JButton getBInsertar(){
    return bInsertar;
}

public JButton getBModificar(){
    return bModificar;
}

public JButton getBEliminar(){
    return bEliminar;
}

public JButton getBFiltrar(){
    return bFiltrar;
}

public JLabel getLIdValor(){
    return lIdValor;
}

public JTextField getTNombre(){
    return tNombre;
}

public JTextField getTEdad(){
    return tEdad;
}

public JTextField getTOficio(){
    return tOficio;
}

public JTextField getTTelefono(){
    return tTelefono;
}

public JTextField getTEmail(){
    return tEmail;
}

public JTextField getTConsulta(){
    return tConsulta;
}
```

Ya esta casi todo listo, falta configurar los eventos, los eventos de **acción** los revisaremos en la siguiente sección, vamos a utilizar nuestros eventos del **Mouse** y de tipo **Enfoque**(Focus) para darle algo de interactividad a nuestro componente, nos ubicamos en nuestra clase **AmigosComponent**.

### **Eventos MouseListener**

Para el caso de los botones vamos a configurar un cambio de color una vez se pase encima de ellos como hemos revisado en anteriores sesiones:
```javascript
 @Override
public void mouseEntered(MouseEvent e) {
    if(e.getSource() instanceof JButton){
        JButton boton = ((JButton) e.getSource());
        boton.setBackground(RecursosService.getService().getColorAzulOscuro());
    }
}

@Override
public void mouseExited(MouseEvent e) {
    if(e.getSource() instanceof JButton){
        JButton boton = ((JButton) e.getSource());
        boton.setBackground(RecursosService.getService().getColorAzul());
    }
}
```
En el anterior código realizamos una **Ejemplificación única de objetos de una misma clase** y ademas una **Discriminación por clases**. Esta ultima parece no ser necesaría ya que solo estamos configurando a un tipo de clase (botones), sin embargo lo realizamos por un motivo que explicaremos una vez tengamos la tabla.


### **Eventos FocusListener**

Ahora vamos a configurar los métodos relacionados con la interfaz **FocusListener**. 

El método **focusGained** realiza una acción cada vez que un elemento esta seleccionado y se esta enfocando en el, en este caso los JTextfield son los que están escuchando este tipo de eventos, queremos que cuando se seleccione cualquiera de estos, cambie a tener un borde azul y en caso de contener el placeholder, este se retire. Para obtener el borde azul vamos a hacer uso del servicio **RecursosService** y para verificar si alguno de estos JTextfield contiene su placeholder correspondiente vamos a ayudarnos de nuestro atributo **placegolders** que contiene todos estos:

```javascript
@Override
public void focusGained(FocusEvent e) {
    JTextField textField = ((JTextField) e.getSource());
    textField.setBorder(RecursosService.getService().getBorderAzul());
    if(
        textField.getText().equals(placeholdes[0]) || textField.getText().equals(placeholdes[1]) ||
        textField.getText().equals(placeholdes[2]) || textField.getText().equals(placeholdes[3]) ||
        textField.getText().equals(placeholdes[4]) || textField.getText().equals(placeholdes[5]) 
    )
        textField.setText("");
}
```
El Método **focusLost** realiza una acción una vez el objeto deja de estar seleccionado y pierde el enfoque, en este caso simplemente queremos que vuelva a estar sin ningún borde:
```javascript
@Override
public void focusLost(FocusEvent e) {
    JTextField textField = ((JTextField) e.getSource());
    textField.setBorder(null);
}
```
Este enfoque es mucho mas acorde al que utilizamos por ejemplo en el login ya que ahora cada JTextfield se ocupa unicamente de su contenido y solo se activa cuando se selecciona sin necesidad de crear una gran cantidad de código ni varias condicionales.

# Creación de JTable y sus partes

Ya tenemos listo todo para poder crear un objeto **JTable** y obtener información a traves de ella. Un objeto **JTable** es un objeto avanzado ya que generalmente no se crea unicamente el objeto y se adiciona como cualquier otro, ya que este depende de algunas partes para su creación completa, a continuación mencionamos los objetos necesarios y su función:
* **JScrollPane**: Este es un tipo de panel especial que se caracteriza por contener Scrolls (Barras laterales) con las que el usuario puede navegar de forma vertical u horizontal, en nuestro caso una tabla puede contener una cantidad considerable de filas o columnas y ademas no tiene un tamaño fijo ya que en tiempo de ejecución se pueden agregar mas filas o columnas, por lo que no se puede agregar simplemente a un panel y especificarle un tamaño fijo, de ser asi mucha información no será visible para el usuario, es por eso que este tipo de panel cobra importancia y gracias a este podremos navegar dentro de la tabla y ver información que esta mas allá de los limites de nuestra interfaz.
* **JPanel**: Este panel es opcional y representa el **Corner** dentro de un ScrollPane, el Corner es el espacio que existe entre los ScrollBar del panel y este panel se usa para cubrir esas zonas y se vea mucho mejor.
* **JTable**: Este es el objeto principal y esta es la representación de la información en forma de tabla que el usuario podrá revisar, ademas es el medio por el cual se tendrá una interacción directa entre el usuario y la información.
* **JTableHeader**: Este objeto se utiliza para dar una personalización gráfica al encabezado de la tabla.
* **DefaultTableModel**: Este objeto es quizás el mas importante, ya que es el que realmente contiene la información y puede ser mostrada en la tabla, es decir que mientras la **JTable** muestra la información de forma organizada al usuario, es el **DefaultTableModel** el que se encarga de gestionar que información es la que contiene dicha tabla.
* **Arreglo tipo Strings** este arreglo es opcional pero muchas veces se utiliza, representa la información que estará en encabezado de la tabla, es decir que son los títulos de cada columna en la tabla.

Vamos a realizar la declaración de cada uno de estos objetos:

```javascript
// Declaración objetos para JTable
private JScrollPane pTabla;
private JPanel pCorner;
private JTable tabla;
private JTableHeader header;
private DefaultTableModel modelo;
private String [] cabecera={"id", "Nombre", "Teléfono", "Email"};
```

Vamos a crear un método que se encargara de crear todo lo relacionado con la Tabla:

```javascript
public void crearJTable(){
}
```

* Lo primero que haremos dentro de este método sera ejemplificar al objeto **DefaultTableModel** que en nuestro caso llamamos **modelo**:
```javascript
public void crearJTable(){
    modelo = new DefaultTableModel();
}
```
* Ahora vamos a agregar la información de los títulos de las columnas (la cabecera), para eso debemos decirle al modelo que se agregará con el método **setColumnIdentifiers** y le pasamos por parámetro al arreglo que contiene los títulos de las columnas en nuestro caso **cabecera**:
```javascript
public void crearJTable(){
    modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(cabecera);
}
```

* Ahora vamos a ejemplificar a nuestra tabla y ademas vamos a agregarle el modelo que previamente configuramos esto con el método **setModel**:
```javascript
public void crearJTable(){
    modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(cabecera);

    tabla = new JTable();
    tabla.setModel(modelo);
}
```

Teniendo nuestra tabla ejemplificada vamos a obtener el **JTableHeader** de la tabla que recordemos sera el objeto para darle estilos a la cabecera de la tabla, por ahora solo realizaremos la obtención y los estilos se verán en la ultima sección. Para obtenerlo usamos el método **getTableHeader()**.

```javascript
public void crearJTable(){
    modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(cabecera);

    tabla = new JTable();
    tabla.setModel(modelo);
    
    header = tabla.getTableHeader();
}
```

Finalmente vamos a crear a nuestro **JScrollPane** que va a contener a nuestra tabla y para esto nos vamos a ayudar de nuestro servicio **ObjGraficoService**.

```javascript
public void crearJTable(){
    modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(cabecera);

    tabla = new JTable();
    tabla.setModel(modelo);
    
    header = tabla.getTableHeader();

    pTabla = sObjGraficos.construirPanelBarra(tabla, 10, 220, 580, 370, Color.WHITE, null);
    this.add(pTabla);
}
```

Noten que el primer argumento que se enviá cuando se construye un **JScrollPane** es un **Componente de java** y en este caso sera nuestra tabla, todos los objetos gráficos de java heredan de un **componente de Java**.

* **Llamada del método en el constructor:**
```javascript
// Dentro del constructor
this.crearJTable();
```

nuestra aplicación luce asi:

<div align='center'>
    <img  src='https://i.imgur.com/4npYdE5.png'>
    <p>Incorporación de la tabla dentro del componente amigos.</p>
</div>

para finalizar vamos a crear los métodos **get** tanto de la tabla (**JTable**) como del modelo (**DefaultTableModel**):
```javascript
public JTable getTabla(){
    return tabla;
}

public DefaultTableModel getModelo(){
    return modelo;
}
```

# Gestión de información dentro de un JTable

Ya hemos incorporado la tabla a nuestra interfaz gráfica, es tiempo de empezar a gestionar la información de los contactos para que puedan ser vistos desde esta. Para lograr aquello vamos a utilizar nuestros botónes del panel **pOpcion**, como recordaremos estos escuchan a los eventos de acción asi que vamos a usarlos para gestionar la información en nuestra tabla. Nos ubicamos en la clase **AmigosComponent** y lo primero que vamos a hacer es la declaración de los métodos que vamos a utilizar:

```javascript
public void restaurarValores(){
}

public void mostrarRegistrosTabla(){
}

public void insertarRegistroTabla(){
}

public void modificarRegistroTabla(){
}

public void eliminarRegistroTabla(){
}

public void filtrarRegistrosTabla(){
}

public void agregarRegistro(Amigo amigo){
}
```
* El método **mostrarRegistrosTabla()** mostrara toda la información externa contenida en el archivo plano en la tabla y se activara una vez oprimimos el botón **bMostrar**.
* El método **ingresarRegistroTabla()** insertará un nuevo registro capturado con lo que escribió el usuario en los JTextfield y se activara una vez oprimimos el botón **bInsertar**.
* El método **modificarRegistroTabla()** modificará un registro seleccionado de la tabla con la información que este en los JTextField y se activara una vez oprimimos el botón **bModificar**.
* El método **eliminarRegistroTabla()** eliminará un registro seleccionado de la tabla y se activara una vez oprimimos el botón **bEliminar**.
* El método **filtrarRegistroTabla()** va a mostrar una consulta de los registros en la tabla basados en lo que escriba el usuario en el JTextField **tFiltrar** y se activara una vez oprimimos el botón **bFiltrar**.
* Los método **restaurarValores()** y **agregarRegistro** son métodos auxiliares que nos van a ayudar con la funcionalidad de los demás métodos y se usaran varias veces.

Bueno entonces debemos configurar los eventos de acción para llamar al método correspondiente dependiendo del boton que se oprima:
```javascript
@Override
public void actionPerformed(ActionEvent e) {
    if(e.getSource() == amigosTemplate.getBMostrar())
        mostrarRegistrosTabla();
    if(e.getSource() == amigosTemplate.getBInsertar())
        insertarRegistroTabla();
    if(e.getSource() == amigosTemplate.getBModificar())
        modificarRegistroTabla();
    if(e.getSource() == amigosTemplate.getBEliminar())
        eliminarRegistroTabla();
    if(e.getSource() == amigosTemplate.getBFiltrar())
        filtrarRegistrosTabla();
}
```

A continuación vamos a configurar todos los métodos paso a paso y explicaremos ciertas particularidades.

## Mostrar registros en la tabla

En este método queremos obtener todos los Amigos que están en el ArrayList que contiene el servicio **AmigoService** y mostrar su información en la tabla. Lo primero que vamos a realizar es un ciclo que recorra el arreglo, para eso necesitaremos saber el tamaño del arreglo, en este caso no podemos realizar un **For Each** ya que el arreglo no esta precisamente dentro del componente, esta en el servicio, para saber el tamaño del arreglo nos podemos ayudar del método **devolverCantidadAmigos()** que tiene el servicio:

```javascript
public void mostrarRegistrosTabla(){
    for(int i=0; i<sAmigos.devolverCantidadAmigos(); i++){

    }
}
```

Ahora para obtener a un amigo nos ayudamos del atributo que creamos previamente **amigo** y lo igualamos con el método del servicio **devolverAmigo()** que nos va a devolver un amigo de acuerdo a su posición, pero debemos enviar como argumento la posición, para esto vamos a enviar a la variable i del ciclo:
```javascript
public void mostrarRegistrosTabla(){
    for(int i=0; i<sAmigos.devolverCantidadAmigos(); i++){
        amigo = sAmigos.devolverAmigo(i);
    }
}
```

Ahora teniendo al objeto **amigo** de la posición actual ahora queremos agregarlo a nuestra tabla, para eso vamos ayudarnos del método **agregarRegistro** que recibe como parámetro a un objeto de tipo **Amigo** asi que le pasamos como argumento al objeto que ya tenemos cargado:

```javascript
public void mostrarRegistrosTabla(){
    for(int i=0; i<sAmigos.devolverCantidadAmigos(); i++){
        amigo = sAmigos.devolverAmigo(i);
        this.agregarRegistro(amigo);
    }
}
```

Sin embargo este método aun no ha sido configurado, vamos a realizar su codificación, como vamos a añadir una nueva fila a la tabla necesitamos llamar al **modelo** de la tabla ya que es este quien contiene la información que se mostrara en la tabla, para esto llamamos al método get correspondiente de la clase compañera **AmigosTemplate**:

```javascript
public void agregarRegistro(Amigo amigo){
    amigosTemplate.getModelo();
}
```

Ahora le vamos a indicar que vamos a adicionar una nueva fila, esto se realiza con el método **addRow**:

```javascript
public void agregarRegistro(Amigo amigo){
    amigosTemplate.getModelo().addRow();
}
```

Para poder insertar una nueva fila debemos crear un nuevo Objeto de tipo arreglo, esto se hace de la siguiente manera:

```javascript
public void agregarRegistro(Amigo amigo){
    amigosTemplate.getModelo().addRow(
        new Object[]{}
    );
}
```

Ahora lo que tendrá ese arreglo adentro, sera la información que contendrá la nueva fila, en este caso queremos el **id, el nombre, el numero telefónico y el email** de cada contacto, para esto nos ayudaremos del objeto **amigo** que ha recibido por parámetro.

```javascript
public void agregarRegistro(Amigo amigo){
    amigosTemplate.getModelo().addRow(
        new Object[]{amigo.getId(), amigo.getNombre(), amigo.getTelefono(), amigo.getEmail()}
    );
}
```

El método **agregarRegistro** ya esta listo, vamos a continuar con nuestro método **mostrarRegistrosTabla**, en teoría ya debe mostrar todos los registros de la tabla, sin embargo hacen falta algunas configuraciones adicionales, por ejemplo debemos cambiar el label que muestra el valor del Id de un contacto por el siguiente numero al ultimo registro en la tabla, ya que si el usuario desea ingresar un nuevo contacto, el ID de este nuevo amigo debe ser siguiente al ultimo registro que esta en la tabla. Para esto vamos a obtener el tamaño del arreglo de amigos ya que su tamaño siempre será la cantidad de contactos mas uno.

```javascript
public void mostrarRegistrosTabla(){
    for(int i=0; i<sAmigos.devolverCantidadAmigos(); i++){
        amigo = sAmigos.devolverAmigo(i);
        this.agregarRegistro(amigo);
    }
    amigosTemplate.getLIdValor().setText(sAmigos.devolverCantidadAmigos()+"");
}
```

Ahora debemos deshabilitar el botón mostrar registros ya que estos ya se están mostrando en la tabla y quedemos evitar que se duplique la información en caso de que el usuario lo vuelva a oprimir, esto lo realizamos con el método **setEnable**:

```javascript
public void mostrarRegistrosTabla(){
    for(int i=0; i<sAmigos.devolverCantidadAmigos(); i++){
        amigo = sAmigos.devolverAmigo(i);
        this.agregarRegistro(amigo);
    }
    amigosTemplate.getLIdValor().setText(sAmigos.devolverCantidadAmigos()+"");
    amigosTemplate.getBMostrar().setEnabled(false);
}
```

Ya esta listo nuestro método, vamos a probarlo:

<div align='center'>
    <img  src='https://i.imgur.com/GqLplms.png'>
    <p>Muestra de registros en la tabla</p>
</div>

Se puede notar que ademas se inhabilito el boton **bMostrar** y el proximo id para ingresar un nuevo contacto quedo en 10.

## Ingresar un nuevo registro a la tabla

Para insertar un registro nuevo debemos obtener todo lo que haya escrito el usuario en los JTextfield del panel **pDatos**, de esta forma podemos agregar una nueva fila ayudándonos del método **agregarRegistro**, vamos a verlo:

* Primero vamos a utilizar a nuestro atributo **amigo** esta vez para crear un nuevo objeto, asi que lo ejemplificamos:

```javascript
public void insertarRegistroTabla(){
    amigo = new Amigo();
}
```

* Ahora vamos a configurar sus atributos, como el Id, el nombre, edad, etc. Con ayuda de los métodos **set**, y le vamos a pasar la información que recibe desde los JTextField.

```javascript
public void insertarRegistroTabla(){
    amigo = new Amigo();
    amigo.setId(sAmigos.devolverCantidadAmigos());
    amigo.setNombre(amigosTemplate.getTNombre().getText());
    amigo.setEdad(amigosTemplate.getTEdad().getText());
    amigo.setOficio(amigosTemplate.getTOficio().getText());
    amigo.setTelefono(amigosTemplate.getTTelefono().getText());
    amigo.setEmail(amigosTemplate.getTEmail().getText());
}
```

* Ya tenemos listo nuestro objeto, ahora vamos a agregar el registro a la tabla con la ayuda del método **agregarRegistro** y le pasamos como argumento al objeto que recién configuramos
```javascript
public void insertarRegistroTabla(){
    amigo = new Amigo();
    amigo.setId(sAmigos.devolverCantidadAmigos());
    amigo.setNombre(amigosTemplate.getTNombre().getText());
    amigo.setEdad(amigosTemplate.getTEdad().getText());
    amigo.setOficio(amigosTemplate.getTOficio().getText());
    amigo.setTelefono(amigosTemplate.getTTelefono().getText());
    amigo.setEmail(amigosTemplate.getTEmail().getText());
    this.agregarRegistro(amigo);
}
```

En teoría nuestro registro ya se muestra en la tabla una vez oprimamos el botón de insertar registro, sin embargo vamos a realizar unas cosas adicionales, por ejemplo vamos a agregar el objeto dentro del arreglo de amigos del servicio para que el numero de contactos total este actualizado, para esto llamaremos al método **agregarAmigo** del servicio. Ademas vamos a dejar todos los JTextfield como estaban por defecto con sus respectivos placeholders y ademas vamos a actualizar el Id para el proximo registro, para esto nos vamos a ayudar del método **restaurarValores**.

```javascript
public void insertarRegistroTabla(){
    amigo = new Amigo();
    amigo.setId(sAmigos.devolverCantidadAmigos());
    amigo.setNombre(amigosTemplate.getTNombre().getText());
    amigo.setEdad(amigosTemplate.getTEdad().getText());
    amigo.setOficio(amigosTemplate.getTOficio().getText());
    amigo.setTelefono(amigosTemplate.getTTelefono().getText());
    amigo.setEmail(amigosTemplate.getTEmail().getText());
    this.agregarRegistro(amigo);
    sAmigos.agregarAmigo(amigo);
    restaurarValores(); 
}
```
sin embargo debemos configurar nuestro método **restaurarValores()**. En este simplemente vamos a dejar los placeholders que tenían por defecto ayudándonos de nuevo del arreglo **placeholders** que el componente tiene como atributo. Para el caso del label **lIdValor** vamos a actualizar su valor con el tamaño del arreglo nuevamente:

```javascript
public void restaurarValores(){
    amigosTemplate.getLIdValor().setText(sAmigos.devolverCantidadAmigos()+"");
    amigosTemplate.getTNombre().setText(placeholdes[0]);
    amigosTemplate.getTEdad().setText(placeholdes[1]);
    amigosTemplate.getTOficio().setText(placeholdes[2]);
    amigosTemplate.getTTelefono().setText(placeholdes[3]);
    amigosTemplate.getTEmail().setText(placeholdes[4]);
}
```

Si probamos nuestra aplicación e insertamos nuevos contactos veremos algo como esto:

<div align='center'>
    <img  src='https://i.imgur.com/AIQOVBu.png'>
    <p>Ingreso de nuevos contactos en la tabla</p>
</div>

## Modificación de un registro en la tabla

Ya tenemos información dentro de la tabla, ahora el usuario tiene la posibilidad de modificar esta información, para esto el usuario puede seleccionar el registro (fila) que quiere modificar y a traves de los JTextfield cambiar esta información. 
Lo primero que debemos hacer es identificar la fila que el usuario ha seleccionado, esto lo haremos con ayuda del método **getSelectedRow** del objeto **JTable**. 

```javascript
public void modificarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
}
```

Ahora bien el usuario puede oprimir el boton modificar sin haber seleccionado ninguna fila y esto puede provocar un error asi que vamos a gestionarlo. Si el usuario no ha seleccionado ninguna fila el método **getSelectedRow** devolverá por defecto el valor de -1, con esto podemos ayudarnos para preguntar si se ha seleccionado una fila a modificar.

```javascript
public void modificarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada != -1){

    }
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila", "Error" , JOptionPane.ERROR_MESSAGE);
}
```

En el anterior if estamos preguntando si el valor devuelto es diferente a -1, de ser asi vamos a realizar la modificación de los datos, en caso contrario mostraremos un mensaje que indique que debe seleccionar una fila.

Como vamos a modificar información de la tabla necesitamos manipularla a traves del **modelo** de la tabla asi que debemos obtenerlo, una vez lo obtenemos vamos a llamar a su método **setValueAt** Que recibe por parámetro 3 cosas:
* La nueva información que queremos poner ahora.
* El numero de la fila seleccionada.
* El numero de la columna.

```javascript
public void modificarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada != -1){
        amigosTemplate.getModelo().setValueAt(
            // Informacion nueva, Numero Fila, Numero Columna
        );
    }
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila", "Error" , JOptionPane.ERROR_MESSAGE);
}
```

Como en la tabla solo se muestran 3 valores editables (Nombre, telefono, email) (el id no lo puede cambiar el usuario) vamos a tomar estos 3 valores de los JTextField correspondientes y los pondremos como la nueva información

```javascript
public void modificarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada != -1){
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTNombre().getText(), fSeleccionada, 1
        );
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTTelefono().getText(), fSeleccionada, 2
        );
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTEmail().getText(), fSeleccionada, 3
        );
    }
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila", "Error" , JOptionPane.ERROR_MESSAGE);
}
```
Como se puede ver en el caso del nombre, pasamos la información que obtenemos del JTextField **tNombre**, la fila sera la que selecciono el usuario y la columna de los nombres siempre sera la numero 1 (la 0 es la del id), lo mismo ocurre con el telefono y email donde obtenemos la información correspondiente del JTextField y cambiamos el valor de la columna que para el telefono siempre sera la numero 2 y para el email será el numero 3.

Si ejecutamos nuestra aplicación podemos ver que si funciona la modificación en la tabla sin embargo un usuario puede modificar mas datos a parte del nombre, telefono o email, ademas el cambio solo se ve en la tabla pero el objeto que contiene la información de ese objeto sigue intacta, asi que es necesario también realizar la modificación de esos datos en el objeto, vamos a obtener el objeto desde el servicio y la posición en el arreglo es justamente la misma que en la posición de la fila en la tabla asi que se la enviamos como argumento:

```javascript
public void modificarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada != -1){
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTNombre().getText(), fSeleccionada, 1
        );
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTTelefono().getText(), fSeleccionada, 2
        );
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTEmail().getText(), fSeleccionada, 3
        );
        amigo = sAmigos.devolverAmigo(fSeleccionada);
    }
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila", "Error" , JOptionPane.ERROR_MESSAGE);
}
```

Una vez obtenido ese objeto, procedemos a modificar sus atributos por la nueva información dejada por el usuario en los JTextField, una vez realizado esto, llamamos al método **restaurarValores** para que los JTextField vuelvan a su estado original:
```javascript
public void modificarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada != -1){
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTNombre().getText(), fSeleccionada, 1
        );
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTTelefono().getText(), fSeleccionada, 2
        );
        amigosTemplate.getModelo().setValueAt(
            amigosTemplate.getTEmail().getText(), fSeleccionada, 3
        );
        amigo = sAmigos.devolverAmigo(fSeleccionada);
        amigo.setNombre(amigosTemplate.getTNombre().getText());
        amigo.setEdad(amigosTemplate.getTEdad().getText());
        amigo.setOficio(amigosTemplate.getTOficio().getText());
        amigo.setTelefono(amigosTemplate.getTTelefono().getText());
        amigo.setEmail(amigosTemplate.getTEmail().getText());
        restaurarValores();
    }
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila", "Error" , JOptionPane.ERROR_MESSAGE);
}
```

Si probamos nuestra aplicación vemos que funciona correctamente pero hay una situation incomoda para el usuario y es que cada vez que quiera modificar la información de un usuario este tendrá que reescribirla en los JTextField modificando a su vez los atributos que necesita actualizar, esto es muy engorroso para nuestros usuarios. Una buena solución a esto puede ser que una vez el usuario seleccione una fila en la tabla, la información de este contacto se coloque automáticamente en los JTextfield y de esa forma el usuario solamente deba modificar lo que necesite.  

Para esto necesitamos adicionarle la escucha de eventos MouseListener a la tabla para que una vez el usuario de click sobre una fila realice la acción previamente descrita 

***Nota:** se realiza con la interfaz MouseListener y no con la de ActionListener ya que un objeto tabla no cuenta con la adición de escucha de eventos de acción*.

<div align='center'>
    <img  src='https://i.imgur.com/Xv7hUXv.png'>
    <p>Adición de escucha de eventos MouseListener</p>
</div>

Vamos al método **mouseClicked** y vamos a configurar esta acción, lo primero que debemos hacer es una discriminación de clase para indicar que solo se hará en caso de que sea la tabla quien active el evento.

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() instanceof JTable){

    }
}
```

Y esta discriminación aunque parece obsoleta es muy importante, recordando un poco en los métodos **mouseEntered y mouseExited** también se realizo una discriminación de clases pero para los botones, y esto es importante ya que ahora tenemos dos clases de objetos que escuchan a estos eventos **Tabla y botones** y aunque cada objeto esta gestionando un evento distinto (mouseClicked en la tabla y mouseEntered con mouseExited para los botones) recordemos que todos estos métodos hacen parte de la misma familia **MouseListener** asi que todos están compaginados de alguna forma, si no realizamos esta discriminación en estos métodos y pasamos sobre la tabla con el puntero del Mouse, el programa sacara un error ya que la tabla al escuchar eventos MouseListener va a entrar al evento MouseEntered y va a tratar de convertirse en un Botón, algo que es imposible, lo mismo sucede si damos un click al boton y va a tratar de buscar información que este no posee.

<div align='center'>
    <img  src='https://i.imgur.com/BPuXSMv.png'>
    <p>Discriminación en métodos separados del MouseListener</p>
</div>

Ahora dentro de la discriminación de clase de la tabla queremos obtener la fila que ha seleccionado el usuario, esto ya lo vimos previamente usando el método **getSelectedRow**:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() instanceof JTable){
        int fila = amigosTemplate.getTabla().getSelectedRow();
    }
}
```

Ahora vamos a obtener el objeto del **amigo** dentro del arreglo que contiene el servicio pasandole como posición la fila seleccionada.

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() instanceof JTable){
        int fila = amigosTemplate.getTabla().getSelectedRow();
        amigo = sAmigos.devolverAmigo(fila);
    }
}
```

Una vez tenemos el amigo seleccionado por el usuario debemos cambiar el valor de los JTextField y del Label del Id con la información del amigo seleccionado y esto con el método **setText** que hemos manejado varias veces en el curso:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() instanceof JTable){
        int fila = amigosTemplate.getTabla().getSelectedRow();
        amigo = sAmigos.devolverAmigo(fila);
        amigosTemplate.getLIdValor().setText(amigo.getId()+"");
        amigosTemplate.getTNombre().setText(amigo.getNombre());
        amigosTemplate.getTEdad().setText(amigo.getEdad());
        amigosTemplate.getTOficio().setText(amigo.getOficio());
        amigosTemplate.getTTelefono().setText(amigo.getTelefono());
        amigosTemplate.getTEmail().setText(amigo.getEmail());
    }
}
```

Ya esta todo listo, ahora cada vez que queramos modificar la información de algún contacto basta con seleccionarlo en la tabla, la información de este se pondrá automáticamente en los JTextField y una vez cambiemos la información necesaria no solo se vera reflejada en la tabla sino que el objeto que contiene esta información también quedara actualizado, ademas los JTextfield tomaran su estado inicial de nuevo.

<div align='center'>
    <img  src='https://i.imgur.com/GPewxiW.png'>
    <p>Implementación de modificación de registros completada</p>
</div>

## Eliminar un registro en la tabla

La acción de eliminar es muy parecida a la de modificar, ya que para eliminar un registro de la tabla debemos nuevamente seleccionarlo desde la tabla, y para que no ocurra un error por no haber seleccionado nada vamos a gestionar de nuevo el error.

```javascript
public void eliminarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada!= -1)
        
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila","Error",JOptionPane.ERROR_MESSAGE);
}
```

Ahora para poder eliminar un registro de la tabla vamos a llamar al **modelo** de esta y luego usar el método **removeRow** que va a pedir como parámetro el numero de la fila que se ha seleccionado.

```javascript
public void eliminarRegistroTabla(){
    int fSeleccionada = amigosTemplate.getTabla().getSelectedRow();
    if(fSeleccionada!= -1)
        amigosTemplate.getModelo().removeRow(fSeleccionada);
    else
        JOptionPane.showMessageDialog(null,"seleccione una fila","Error",JOptionPane.ERROR_MESSAGE);
}
```

En este caso solo eliminaremos el registro de la tabla y no del arreglo ya que no se acostumbra eliminar objetos de los arreglos.

## Filtrar registros de la tabla

Los filtros dentro de nuestra tabla se usan a menudo cuando existe una cantidad considerable de registros dentro de esta y queremos buscar alguno en especifico o algunos bajo algún criterio. Para realizar esto debemos llamar a un objeto especial que se encarga de realizar estos filtros. Este objeto es un **TableRowSorter** este objeto crea una copia del modelo de la tabla (quien contiene la información de esta) por lo que es un arreglo dinámico que podrá cambiar su contenido de acuerdo a un criterio, primero vamos a ejemplificarlo y como este sera una copia del **modelo**, al momento de la ejemplificación debemos enviar al modelo como argumento.

```javascript
public void filtrarRegistrosTabla(){
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(amigosTemplate.getModelo());
}
```

Ahora debemos indicarle a la tabla que va a contener este elemento y asi puede estar preparada para realizar un filtro de la información, esto lo realizamos con el método **setRowStorter**:

```javascript
public void filtrarRegistrosTabla(){
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(amigosTemplate.getModelo());
    amigosTemplate.getTabla().setRowSorter(trs);
}
```

Finalmente vamos a realizar el filtrado de filas, para esto necesitamos primero indicarle al **TableRowSorter** que vamos a configurar un filtro a traves del método **setRowFilter**:
```javascript
public void filtrarRegistrosTabla(){
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(amigosTemplate.getModelo());
    amigosTemplate.getTabla().setRowSorter(trs);
    trs.setRowFilter();
}
```

Dentro del método y como argumento debemos crear el filtro, para esto debemos llamar al objeto **RowFilter** que se encarga de dejar unicamente las filas que cumplen con el criterio de filtrado
```javascript
public void filtrarRegistrosTabla(){
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(amigosTemplate.getModelo());
    amigosTemplate.getTabla().setRowSorter(trs);
    trs.setRowFilter(RowFilter);
}
```

Pero es necesario para hacer efectivo el filtro la llamada a su método **regexFilter** que recibe como parámetro al criterio del filtrado, que en este caso va a ser lo que escriba el usuario dentro del JTextfield **tFiltrar**.

```javascript
public void filtrarRegistrosTabla(){
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(amigosTemplate.getModelo());
    amigosTemplate.getTabla().setRowSorter(trs);
    trs.setRowFilter(RowFilter.regexFilter(amigosTemplate.getTConsulta().getText()));
}
```

Nuestro filtro esta listo, ahora si corremos nuestra aplicación, escribimos algún criterio de filtro en el JTextField **tFiltrar** y luego oprimimos el boton **bFiltrar** veremos algo como esto:

<div align='center'>
    <img  src='https://i.imgur.com/bfJV5NM.png'>
    <p>Incorporación de filtro dentro de la tabla</p>
</div>

# Personalización del JTable

Nuestra tabla funciona de maravilla y ya podemos gestionar la información a traves de ella. Sin embargo, esta tabla se ve un poco mal con respecto a la interfaz que hemos estado manejando, es hora de personalizarla un poco y que tome un mejor aspecto. Vamos a dirigirnos al método **crearTable** de la clase **AmigosTemplate** y vamos a realizar algunas configuraciones que le darán un mejor aspecto.

Para empezar podemos agregarle un alto a cada fila, ya que las filas están muy pegadas la una de la otra, esto con el método **setRowHeight** que recibe como parámetro un entero que representa la altura de cada fila. También podemos quitar las lineas que separan cada fila y cada columna, esto va dependiendo del diseño de cada desarrollador en este caso creemos que se vera mejor si omitimos estas lineas, para ocultar estas lineas podemos usar los métodos **setShowHorizontalLines y setShowVerticalLines**, ambos reciben como parámetro un booleano que si se deja en false, ocultara estas lineas

```javascript
public void crearJTable(){
    tabla.setRowHeight(40);
    tabla.setShowHorizontalLines(false);
    tabla.setShowVerticalLines(false);
}
```

<div align='center'>
    <img  src='https://i.imgur.com/dyIpdas.png'>
    <p>Tabla con filas mas altas y sin las lineas que separan los registros</p>
</div>

Nuestra cabecera de la tabla esta un poco angosta ahora con respecto a los registros de la tabla, vamos a añadirle mas altura, para esto debemos tomar el objeto **header** que ya obtuvimos previamente de la tabla. Para darle un tamaño al header tenemos que utilizar el método **setPreferredSize**. 

```javascript
header.setPreferredSize();
```

Este método nos va a pedir por parámetro la creación de una dimension que es un objeto en java para darle tamaño a objetos especiales, asi que lo crearemos:

```javascript
header.setPreferredSize(new Dimension());
```

Para finalizar, dentro del constructor de la Dimension que acabamos de crear debemos pasarle dos parámetros que serán el ancho y el alto, dejaremos el ancho igual al **ScrollPane** pero e alto lo dejaremos en 30 pixeles.

```javascript
header.setPreferredSize(new Dimension(580, 30));
```

<div align='center'>
    <img  src='https://i.imgur.com/AYZqrKQ.png'>
    <p>Tabla con cabecera más grande</p>
</div>

A continuación vamos a crear un nuevo servicio Gráfico para decorar mejor nuestra tabla, el propósito de esta clase no sera explicar que hacen estos métodos, vamos a dedicar una clase entera para explicar que hacen estos métodos, por ahora solo lo vamos a implementar.

## Preparando detalles para personalizar nuestra tabla

Vamos a crear un servicio llamado **GraficosAvanzadosService** y lo dejaremos dentro del paquete **ServiceGraphics**

<div align='center'>
    <img  src='https://i.imgur.com/cu9wU7L.png'>
    <p>Creación de servicio GraficosAvanzadosService</p>
</div>

Este servicio ademas de la estructura básica de un servicio contiene varios métodos que realizan ciertas acciones las cuales en esta clase no vamos a explicar. Para obtener el código de este servicio usted puede copiarlo de este mismo repositorio entrando a las carpetas **Clase11/src/app/services/servicesGraphics/GraficosAvanzadosService** ahi encontrara el código.

<div align='center'>
    <img  src='https://i.imgur.com/5xjRQbj.png'>
    <p>Código dentro del servicio GraficosAvanzadosService</p>
</div>

Vamos a obtener ese servicio en nuestra clase **AmigosTemplate**

* **Declaración:**
```javascript
private GraficosAvanzadosService sGraficosAvanzados;
```

* **Obtención del servicio:**
```javascript
// Dentro del constructor
this.sGraficosAvanzados = GraficosAvanzadosService.getService();
```

Primero vamos a personalizar la cabecera de nuestra tabla, vamos a añadirle un color de fondo azul y un color de fuente blanco, para eso debemos llamar al método **setDefaultRenderer** de nuestra cabecera:

```javascript
header.setDefaultRenderer();
```

Dentro de este método, como parámetro vamos a tener que crear un objeto **DefaultRender**, pero para esto tenemos un método en nuestro servicio llamado **devolverTablaPersonalizada**, que nos va a pedir varias cosas como parámetro:

* **ColorPrincipal:** Es el color principal que va a tener la tabla.
* **ColorSecundario:** Es el color que complementa al color principal, si se pasan los dos colores la primera fila va a tomar el color primario, la segunda fila el color secundario y asi sucesivamente.
* **ColorSeleccion:** Es el color que se va a dibujar en la tabla una vez se seleccione una fila de la tabla.
* **ColorFuente:** Es el color que va a tener la fuente de la tabla.
* **Fuente:** Es el tipo de fuente que tendrá la tabla.

Para el caso del Header, como este solo representa una fila unicamente vamos a enviar el color principal, el color de fuente y el tipo de fuente:

```javascript
header.setDefaultRenderer(sGraficosAvanzados.devolverTablaPersonalizada(
    sRecursos.getColorAzul(), null , null, Color.WHITE, sRecursos.getFontPequeña()
));
```

Vamos a hacer lo mismo para la tabla, pero esta vez vamos a enviar todos los parámetros:

```javascript
tabla.setDefaultRenderer(Object.class, sGraficosAvanzados.devolverTablaPersonalizada(
    Color.WHITE, sRecursos.getColorGrisClaro() , sRecursos.getColorAzulOscuro(), 
    sRecursos.getColorGrisOscuro(), sRecursos.getFontPequeña()
));
```

Vamos a ver como se ve nuestra interfaz:

<div align='center'>
    <img  src='https://i.imgur.com/ctKJr4J.png'>
    <p>Tabla personalizada usando el servicio GraficosAvanzadosService</p>
</div>


Ahora vamos a cambiar el Scroll que acompaña a la tabla una vez hay muchos registros, este Scroll por defecto es algo anticuado y no se ve bien con respecto al resto de nuestra interfaz. Para esto le vamos a indicar a nuestro PaneScroll **pTabla** que vamos a editar el Scroll vertical, asi que debemos obtenerlo primero:

```javascript
pTabla.getVerticalScrollBar();
```

Luego para darle una personalización debemos llamar al método **setUI** que va a pedir por parámetro un objeto tipo **BasicScrollBarUI** y con ayuda de nuestro servicio **GraficosAvanzadosService** y su método **devolverScrollPersonalizado** podemos realizar dicha personalización.

Este método va a pedir por parámetro:

* El Grosor de la barra del Scroll.
* El Radio de las esquinas de la barra del Scroll.
* El color de fondo del ScrollBar.
* El color de la barra.
* El color de la barra una vez se esta arrastrando (moviendo con el botón del mouse oprimido).

```javascript
pTabla.getVerticalScrollBar().setUI(
    sGraficosAvanzados.devolverScrollPersonalizado(
        7, 10, Color.WHITE, Color.GRAY, sRecursos.getColorGrisOscuro()
    )
);
```

Nuestra tabla se vera de la siguiente manera:

<div align='center'>
    <img  src='https://i.imgur.com/yByBis9.png'>
    <p>Personalización del Scroll de la tabla.</p>
</div>

Por ultimo podemos notar que en la parte superior izquierda al lado de la cabecera hay un espacio que sobra, este espacio es conocido como el **Corner** del ScrollBar y podemos editarlo para que luzca como si fuera parte de la cabecera. Para esto vamos a usar nuestro panel **pCorner**, lo primero que haremos es ejemplificarlo de forma tradicional:

```javascript
// Dentro del método crearJTable
pCorner = new JPanel();
```
Ahora vamos a indicarle que tenga un color de fondo azul al igual que la cabecera:
```javascript
// Dentro del método crearJTable
pCorner = new JPanel();
pCorner.setBackground(sRecursos.getColorAzul());
```

Por ultimo vamos a configurar ese **Corner** a nuestro **ScrollPane**, esto mediante el método **setCorner** que va a pedir por parámetros 2 cosas:
* **Ubicación del corner:** Esto debido a que puede existir un espacio sobrante en distintas partes del **JScrollPane**, asi que hay que especificar en que esquina esta ubicado el Corner a editar.
* **Corner:** Es el objeto gráfico que va a cubrir el espacio, en este caso un panel.

```javascript
// Dentro del método crearJTable
pCorner = new JPanel();
pCorner.setBackground(sRecursos.getColorAzul());
pTabla.setCorner(JScrollPane.UPPER_RIGHT_CORNER, pCorner);
```

Finalmente nuestra tabla se ve asi:
<div align='center'>
    <img  src='https://i.imgur.com/K8JYhqm.png'>
    <p>Personalización total de la tabla.</p>
</div>

# Resultado 

Si has llegado hasta aquí **!Felicidades!** has aprendido las partes para construir tablas, como gestionar información a traves de Tablas, como dar cierta interactividad a estas y ademas has aprendido como personalizarlas. En la siguiente clase vamos a ver animaciones para darle ese toque final de interactividad a nuestro proyecto.

# Actividad 

Hacer uso de tablas dentro de sus respectivos proyectos para la gestión de la información y realizar personalización de estas con ayuda de nuestro nuevo servicio gráfico.