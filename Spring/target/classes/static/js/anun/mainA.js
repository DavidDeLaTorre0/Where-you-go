google.maps.event.addDomListener(window,"load", ()=>{
    const user_location = new UserLocation(()=>{

        const mapOptions = {
            zoom: 14,
            center: {
                lat:user_location.latitud,
                lng:user_location.longitud
            }
        };

        const mapa_element = document.getElementById('map');

        const map = new google.maps.Map(mapa_element,mapOptions);


        const search_input_origin =document.getElementById('search-place-origin');
        const search_input =document.getElementById('search-place');

        const autocomplete_origin = new google.maps.places.Autocomplete(search_input_origin);
        const autocomplete = new google.maps.places.Autocomplete(search_input);
        

        const marker = new google.maps.Marker({

            map: map
        });


        autocomplete.bindTo("bounds",map);

        google.maps.event.addListener(autocomplete_origin,"place_changed",()=>{

            if(autocomplete_origin == "" ||autocomplete_origin ==null){  
            }else{
                const place = autocomplete_origin.getPlace();
                console.log(place.formatted_address);
               
                if(place.geometry.viewport){
                    map.fitBounds(place.geometry.viewport);
                }else{
                    
                    map.setCenter(place.geometry.location);
                    map.setZoom(15);
                }

                marker.setPlace({
                    placeId: place.place_id,
                    location: place.geometry.location
                });

                marker.setVisible(true);
                setOrigen(place.geometry.location);
                document.getElementById("search-place-origin").value= place.formatted_address;
                calcularDistance();
            }
        });

        google.maps.event.addListener(autocomplete,"place_changed",()=>{

            if(autocomplete == "" ||autocomplete==null){
                alert("Rellene todos los campos por favor");   
            }else{
                const place = autocomplete.getPlace();
                console.log(place.formatted_address);
                if(place.geometry.viewport){
                    map.fitBounds(place.geometry.viewport);
                }else{
                    map.setCenter(place.geometry.location);
                    map.setZoom(15);
                }

                marker.setPlace({
                    placeId: place.place_id,
                    location: place.geometry.location
                });

                marker.setVisible(true);
                setDestino(place.geometry.location);
                document.getElementById("search-place").value= place.formatted_address;
                calcularDistance();
            }
        });
        
    });
});
   

function setOrigen(origen){
    this.origen=origen;
}
function getOrigen(){
    return origen;
}

function setDestino(destino){
    this.destino=destino;
}

function getDestino(){
    return destino;
}
function calcularDistance(){

    var origin = new google.maps.LatLng(getOrigen());

    var destino = new google.maps.LatLng(getDestino());
    var service = new google.maps.DistanceMatrixService();

    service.getDistanceMatrix({
        origins:[origin],
        destinations: [destino],
        travelMode: google.maps.TravelMode.DRIVING
    },(respuesta,status)=>{
        const info = respuesta.rows[0].elements[0];

        const distancia = info.distance.text;
            console.log(distancia);
        const duracion = info.duration.text;
            console.log(duracion);
        document.getElementById("infoDis").innerHTML=`
            La distancia del viaje es: ${distancia}
        `;
        document.getElementById("infoDur").innerHTML=`
            Duracion del viaje: ${duracion} de dicho destino
        `;
        
        
        document.getElementById("tiempo").value= duracion;
        document.getElementById("distancia").value= distancia;
    })
}
