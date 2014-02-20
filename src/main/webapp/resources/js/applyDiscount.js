//Function that makes work select2 in jquery dialog
var ui_dialog_interaction; // moves outside function scope in order to keep persistent value


var availableTours;
var availableDiscounts;

var currentUrl = window.location.href;
var cutUrl = currentUrl.substr(0, currentUrl.lastIndexOf("/"));

$.fn.replaceWithPush = function (a) {
    var $a = $(a);
    this.replaceWith($a);
    return $a;
};

function downloadAvailableTours() {
//Get agency tours
    $.ajax({
        url: cutUrl + "/tours/getAgencyToursWithoutDiscounts",
        dataType: "json",
        success: function (data, textStatus) {
            availableTours = data;
        }
    });
}
function findIndexById(data, id) {
    var foundId = -1;
    $.each(data, function (i, tourName) {
        if (i === id) {
            foundId = i;
        }
    });
    return foundId;
}
$(document).ready(function () {


    //Get agency discount policies
    $.ajax({
        url: cutUrl + "/discount_policy/getAllDiscounts",
        dataType: "json",
        success: function (data, textStatus) {
            availableDiscounts = data;
        }
    });
    downloadAvailableTours();
    //setup the jtable that will display the results
    $('#records').jtable({
        title: 'Apply discount policy',
        actions: {
            listAction: currentUrl + '/getAllRecords',
            createAction: currentUrl + '/add',
            updateAction: currentUrl + '/edit',
            deleteAction: currentUrl + '/delete'
        },
        fields: {
            tourId: {
                title: 'id',
                key: true,
                create: false,
                edit: false,
                list: false

            }, name: {
                title: "Tour",
                width: "50%",
                type: "combobox",
                display: function (data) {
                    return data.record.name;
                }
            }, discount_policy: {
                title: "Discount policy(-ies)",
                width: "50%",
                display: function (data) {
                    console.log(discountPolicies);
                    var discountPolicies = data.record.discountPolicies;
                    var str = "";
                    $.each(discountPolicies, function (index, element) {
                        str += "<br>" + element.name + "</br>";

                    });
                    return str;
                }
            }
        }, formCreated: function (event, data) {
            console.log("Events", $._data($('.jtable-toolbar-item-add-record'), "events"));
            if (Object.keys(availableTours).length == 0 && data.formType == 'create') {

                openDialog("Not found tours to add");
                data.form.parent().parent().find("span:contains(Cancel)").parent().click();
            }
            var tourInput = data.form.find("input[name='name']");
            tourInput = tourInput.replaceWithPush($("<select id='tours' name='tours' required='' class='select2'  multiple=''><select>"));

            var discountInput = data.form.find("input[name=discount_policy]");
            discountInput = discountInput.replaceWithPush($("<select id='discount_policies' required=''  class='select2' name='discount_policies' multiple=''><select>"));
            console.log("Tour", tourInput, 'Discount', discountInput);

            $.each(availableDiscounts, function (value, text) {
                discountInput.append($('<option>', {
                    value: value,
                    text: text
                }));
            });
            if (data.formType == "edit") {
                console.log("Edit disc pol", data.record.discountPolicies);
                $.each(data.record.discountPolicies, function (i, discountPolicy) {
                    discountInput.find('[value=' + discountPolicy.discountPolicyId + ']').attr('selected', true);
                    console.log("Disc pol id", '[value=' + discountPolicy.discountPolicyId + ']');
                });
                tourInput.append($('<option>', {
                    value: data.record.tourId,
                    text: data.record.name
                }));
                tourInput.val(data.record.tourId);

            } else {
                $.each(availableTours, function (value, text) {
                    tourInput.append($('<option>', {
                        value: value,
                        text: text
                    }));
                });

            }


            $(".select2").select2({width: "300px"});

        }, formSubmitting: function (data) {
            var submit = true;
            var message = "";
            if ($("#s2id_tours").select2("val") == "") {
                message += "Field tour is required<br>";
                submit = false;
            }
            if ($("#s2id_discount_policies").select2("val") == "") {
                message += "Field discount policy is required";
                submit = false;
            }
            if (message.length > 0) {
                openDialog(message);
            }
            return submit;
        },
        //Register to selectionChanged event to hanlde events
        recordAdded: function (event, data) {
            console.log("Available tours add 1 ", availableTours);
            $.each(data.serverResponse.Records, function (i, tour) {
                var tourId = tour.tourId;
                delete availableTours[tourId];
                console.log(tour);
                if (i > 0) {
                    $('#records').jtable('addRecord', {record: tour, clientOnly: true});
                }
            });

            console.log("Available tours add 2 ", availableTours);


        }, recordDeleted: function (event, data) {
            var deletedTour = data.record;
            availableTours[deletedTour.tourId] = deletedTour.name;
            console.log(availableTours)
        }

    });
    $('#records').jtable('load');


});


function openDialog(message) {
    if ($('#dialog').length == 0) {
        $(document.body).append('<div id="dialog">' + message + '</div>');
    } else {
        $('#dialog').html(message);
    }
    $("#dialog").dialog({
        autoOpen: false,
        show: "blind",
        hide: "fade",
        title: "Error",
        buttons: {Ok: function () {
            $(this).dialog("close");
        }}
    });
    $("#dialog").dialog("open");

};
function createSelect(name, options) {
    var select = document.createElement("select");
    select.name = name;
    for (var item in options) {
        var option = document.createElement("option");
        option.value = item;
        option.text = options[item];
        select.appendChild(option);
    }
    return select;
}
