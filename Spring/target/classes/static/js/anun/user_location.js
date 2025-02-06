class UserLocation{
    constructor(callback){

        if(navigator.geolocation){
            navigator.geolocation.getCurrentPosition((localizacion) =>{
                this.latitud= localizacion.coords.latitude;
                this.longitud= localizacion.coords.longitude;
                callback()
            })
        }else{
            alert("Tu navegador no soporta las funcionalidades de esta pagina");
        }
    }
}

