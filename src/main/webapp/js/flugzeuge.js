/**
 * View-Controller für flugzeuge.html
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 30.06.2021
 */

$(document).ready(function () {
    loadFlugzeuge();

    /**
     * listener for buttons within shelfForm
     */

    $("#shelfForm").on("click", "button", function () {
        if (confirm("Wollen Sie dieses Buch wirklich löschen?")) {
            deleteFlugzeug(this.value);
        }
    });

});


function loadFlugzeuge() {
    $
        .ajax({
            url: "./markt/flugzeug/list",
            dataType: "json",
            type: "GET"
        })
        .done(showFlugzeuge)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href = "./login.html";
            } else if (xhr.status == 404) {
                $("#message").text("keine Flugzeuge vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen der Flugzeuge");
            }
        })
}


function showFlugzeuge(flugzeugData) {

    let table = document.getElementById("flugzeuge");
    //clearTable(table);

    $.each(flugzeugData, function (flugzeugUUID, flugzeug) {
        let row = table.insertRow(-1);

        let cell = row.insertCell(-1);
        cell.innerHTML = flugzeug.flugzeugtyp;

        cell = row.insertCell(-1);
        cell.innerHTML = flugzeug.hersteller.name;

        cell = row.insertCell(-1);
        if (flugzeug.airline == null){
            cell.innerHTML = "";
        }
        else {
            cell.innerHTML = flugzeug.airline.name;
        }

        cell = row.insertCell(-1);
        cell.innerHTML = flugzeug.herstellungsdatum;

        cell = row.insertCell(-1);
        cell.innerHTML = flugzeug.flugzeugUUID;

        cell = row.insertCell(-1);
        cell.innerHTML = "<a href='./flugzeugedit.html?flugzeugUUID=" + flugzeugUUID + "'>Bearbeiten</a>";

        cell = row.insertCell(-1);
        cell.innerHTML = "<button type='button' id='delete_" + flugzeugUUID + "' value='" + flugzeugUUID + "'>Löschen</button>";

    });
}

function clearTable(table) {
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}


function deleteFlugzeug(flugzeugUUID) {
    $
        .ajax({
            url: "./markt/flugzeug/delete?flugzeugUUID=" + flugzeugUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function (data) {
            loadFlugzeuge();
            $("#message").text("Flugzeug gelöscht");

        })
        .fail(function (xhr, status, errorThrown) {
            $("#message").text("Fehler beim Löschen des Flugzeugs");
        })
}