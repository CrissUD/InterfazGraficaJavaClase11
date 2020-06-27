package app.client.components.inicio;

import app.services.servicesLogic.AccionService;
import models.Accion;

public class InicioComponent {

    private InicioTemplate inicioTemplate;
    private AccionService sAccion;

    public InicioComponent(){
        sAccion = AccionService.getService();
        this.inicioTemplate=  new InicioTemplate(this);
    }
    
    public Accion obtenerAccion(int numeroAccion){
        return sAccion.devolverAccion(numeroAccion);
    }

    public InicioTemplate getInicioTemplate() {
        return this.inicioTemplate;
    }
}