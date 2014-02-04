var availableFields;
$(document).ready(function () {
    //setup the jtable that will display the results
    var currentUrl = window.location.href;
    var url = currentUrl + "/getAvailableFields";
    $.ajax({
        url: url,
        dataType: "json",
        success: function (data, textStatus) {
            availableFields = data;
        }
    });
    $('#dependencies').jtable({
        title: 'Discount policies',
        actions: {
            listAction: currentUrl + '/getAllDependencies',
            createAction: currentUrl + '/add',
            updateAction: currentUrl + '/edit',
            deleteAction: currentUrl + '/delete'
        },
        fields: {
            id: {
                title: 'id',
                key: true,
                list: false,
                create: false,
                edit: false
            }, tag: {
                title: "Tag",
                width: "20%"
            }, tableField: {
                title: "Table field",
                width: "20%",
                options: availableFields
            }, description: {
                title: "Description",
                width: "60%"
            }
        }, formCreated: function (event, data) {
            var $availableFieds = $('<select name="tableField" required="required"></select>');
            $.each(availableFields, function (i, item) {
                $availableFieds.append($('<option>', {
                    value: item,
                    text: item
                }));
            });
            data.form.find("input[name=tableField]").replaceWith($availableFieds);
            $availableFieds.select2({width: "300px"});

        },
        rowInserted: function (event, data) {
            $('#dependencies').jtable('selectRows', data.row);

        },
        //Register to selectionChanged event to hanlde events
        recordAdded: function (event, data) {
            //after record insertion, reload the records
            $('#dependencies').jtable('load');
        },
        recordUpdated: function (event, data) {
            //after record updation, reload the records
            $('#dependencies').jtable('load');
        }
    });
    $('#dependencies').jtable('load');

});