/**
 * View-Controller für flugzeugedit.html
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 1.07.2021
 */

$(document).ready(function () {
    loadHersteller();
    loadAirlines();
    loadFlugzeug();

    /**
     * listener for submitting the form
     */
    $("#flugzeugeditForm").submit(saveFlugzeug);

    /**
     * listener for button [abbrechen], redirects to bookshelf
     */
    $("#cancel").click(function () {
        window.location.href = "./flugzeuge.html";
    });


});

function loadFlugzeug() {
    let alteFlugzeugUUID = $.urlParam("flugzeugUUID");
    $("#alteFlugzeugUUID").val(alteFlugzeugUUID);
    if (alteFlugzeugUUID) {
        $
            .ajax({
                url: "./markt/flugzeug/check?flugzeugUUID=" + alteFlugzeugUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showFlugzeug)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 403) {
                    window.location.href = "./login.html";
                } else if (xhr.status == 404) {
                    $("#message").text("Kein Flugzeug gefunden");
                } else {
                    window.location.href = "./flugzeuge.html";
                }
            })
    }

}


function loadHersteller() {
    $
        .ajax({
            url: "./markt/hersteller/list",
            dataType: "json",
            type: "GET"
        })
        .done(showHersteller)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Keinen Hersteller gefunden");
            } else {
                window.location.href = "./flugzeuge.html";
            }
        })
}

function loadAirlines() {
    $
        .ajax({
            url: "./markt/airline/list",
            dataType: "json",
            type: "GET"
        })
        .done(showAirlines)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Keine Airline gefunden");
            } else {
                window.location.href = "./flugzeuge.html";
            }
        })
}


function showFlugzeug(flugzeug) {
    $("#hersteller").val(flugzeug.hersteller.herstellerUUID);
    if (flugzeug.airline == null){
        $("#airline").val("-");
    }
    else {
        $("#airline").val(flugzeug.airline.airlineUUID);
    }
    $("#flugzeugtyp").val(flugzeug.flugzeugtyp);
    $("#herstellungsdatum").val(flugzeug.herstellungsdatum);
    $("#flugzeugUUID").val(flugzeug.flugzeugUUID);
}


function showHersteller(hersteller) {

    $.each(hersteller, function (herstellerUUID, hersteller) {
        $('#hersteller').append($('<option>', {
            value: hersteller.herstellerUUID,
            text: hersteller.name
        }));
    });

}


function showAirlines(airlines) {

    $.each(airlines, function (airlineUUID, airline) {
        $('#airline').append($('<option>', {
            value: airline.airlineUUID,
            text: airline.name
        }));
    });

    //Leere Option, falls das Flugzeug dem Hersteller gehört
    $('#airline').append($('<option>', {
        value: "-",
        text: "-"
    }));
}


function saveFlugzeug(form) {
    form.preventDefault();

    $("#neueHerstellerUUID").val($("#hersteller").val());
    $("#neueAirlineUUID").val($("#airline").val());

    let url = "./markt/flugzeug/";
    let type;

    let alteFlugzeugUUID = $("#alteFlugzeugUUID").val();
    if (alteFlugzeugUUID) {
        type= "PUT";
        url += "update"
    } else {
        type = "POST";
        url += "insert";
    }

    $
        .ajax({
            url: url,
            dataType: "text",
            type: type,
            data: $("#flugzeugeditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./flugzeuge.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Dieses Flugzeug existiert nicht");
            } else {
                $("#message").text("Fehler beim Speichern des Flugzeugs");
            }
        })
}