/**
 *Adds condition to discount policy on add or edit discount policy pages
 */
var signsDef = {'>': '>', '>=': "≥", '<': '<', '<=': '≤', '=': '='};
var MAX_NUMB_OF_CONDITIONS = 6;
function addCondition(relation, condition) {

    var divCondition = document.getElementById("condition");
    if (divCondition.children.length > MAX_NUMB_OF_CONDITIONS) {
        openDialog("Maximum number of conditions is exceeded");
        return;
    }
    var conditions = {
        'users.age ': 'User\'s age',
        "users.sex": "User\'s sex",
        "users.name": "User\'s name ",
        "orders.numberOfAdults": "Number of adults places in order",
        "orders.numberOfChildren": "Number of children in order",
        "Day of week": "Day of week"};
    /*
     Form elements
     */
    var divRelationAndCondition = document.createElement("div");
    divRelationAndCondition.className = "relationAndCondition";
    if (divCondition.children.length > 0) {
        var divRow = document.createElement("div");
        divRow.className = "row";
        var divColSm1 = document.createElement("div");
        divColSm1.className = "col-sm-1";
        divRow.appendChild(divColSm1);
        divRelationAndCondition.appendChild(divRow);
        var selectRelation = document.createElement("select");
        selectRelation.name = "relation";
        var option = document.createElement("option");
        option.value = "AND";
        option.text = "And";
        selectRelation.appendChild(option);
        var option = document.createElement("option");
        option.value = "OR";
        option.text = "Or";
        selectRelation.appendChild(option);
        divColSm1.appendChild(selectRelation);
    }
    var divRow = document.createElement("div");
    divRow.className = "row";
    divRow.innerHTML = "&nbsp;";
    divRelationAndCondition.appendChild(divRow);
    var divRow = document.createElement("div");
    divRow.className = "row";
    var divColSm5 = document.createElement("div");
    divColSm5.className = "col-sm-5";
    divRelationAndCondition.appendChild(divRow);
    divRow.appendChild(divColSm5);
    var selectCondition = createSelect('condition', conditions);
    selectCondition.addEventListener('change', function () {
        checkSign(divOperator, selectCondition);
    });
    divColSm5.appendChild(selectCondition);
    var divOperator = document.createElement("div");
    divRow.appendChild(divOperator);
    divOperator.className = "operator col-sm-6";
    var divSign = document.createElement("div");
    divSign.className = "col-sm-4";
    divOperator.appendChild(divSign);
    var selectSign = createSelect('sign', signsDef);
    divSign.appendChild(selectSign);
    var divParam = document.createElement("div");
    divParam.className = "col-sm-8";
    divOperator.appendChild(divParam);
    var param = document.createElement("input");
    param.type = "number";
    param.name = "param";
    divParam.appendChild(param);
    var buttonDelete = document.createElement("button");
    buttonDelete.className = "btn btn-danger  btn-xs ";
    buttonDelete.type = "button";
    buttonDelete.addEventListener('click', function () {
        if (divRelationAndCondition.previousSibling == null) {
            var nextDiv = divRelationAndCondition.nextElementSibling;
            if (nextDiv != null) {
                nextDiv.removeChild(nextDiv.firstChild);
            }

        }
        divRelationAndCondition.parentNode.removeChild(divRelationAndCondition);
        var condition = document.getElementById('condition');
        console.log("Delete scrollheight", condition.scrollHeight);
        if (condition.scrollHeight <= 150) {
            condition.style = "";
        }

    });
    var deleteIcon = document.createElement("i");
    deleteIcon.className = "glyphicon glyphicon-remove-circle";
    buttonDelete.appendChild(deleteIcon);
    divRow.appendChild(buttonDelete);
    if (relation != null) {
        setSelectValue(selectRelation, relation);
    }
    /*
     Split condition on elements.Example users.age>20.Result ["users.age",">","20"]
     */
    if (condition != null) {
        regex = /=<|>=|<|>|=|CONTAINS/;
        cond = condition.replace(regex, ",$&,");
        var elements = cond.split(",");
        setSelectValue(selectCondition, elements[0]);
        checkSign(divOperator, selectCondition);
        selectSign = divOperator.firstElementChild.firstChild;
        if (selectSign != null) {
            setSelectValue(selectSign, elements[1].toLowerCase());
        }
        param = divOperator.childNodes[1].firstChild;
        if (param.tagName.toLowerCase() == "select") {
            setSelectValue(param, elements[2]);
        } else {
            param.value = elements[2];
        }

    }
    divCondition.appendChild(divRelationAndCondition);
    var height = Math.max(divCondition.scrollHeight, divCondition.offsetHeight,
        divCondition.clientHeight);
    if (divCondition.scrollHeight > 150) {
        divCondition.style.height = "150px";
        divCondition.style.overflowY = "auto";
        divCondition.style.overflowX = "hidden";

    }
    $('#ui-id-3').dialog('option', 'position', 'center');

}
function setSelectValue(select, value) {
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].value === value) {
            select.selectedIndex = i;
            break;
        }
    }
}

/**
 * @param relation-relation between condition(Can be And or OR)
 * @param condition-condition to set
 */
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
function cutUrl(str) {
    //TODO:rewrite with help of regex
    str = str.substr(0, str.length - 1);
    str = str.substr(0, str.lastIndexOf("/"));
    return str;
}
/**
 *  Checks condition and sets right operator for selected condition
 * @param e-condition
 */
function checkSign(divOperator, e) {
    var genders = {'Male': 'Male', 'Female': 'Female'};
    var signsName = {'=': '=', 'contains': 'Contains'};
    var dates = {'0': 'Monday', '1': 'Tuesday', '2': 'Wednsday', '3': 'Thursday', '4': 'Friday', '5': 'Saturday', '6': 'Sunday'};
    console.log(divOperator.children.length);
    var selectSign = divOperator.firstChild.firstChild;
    var param = divOperator.children[1].firstChild;
    if (e.selectedIndex == 1) {
        //Gender
        if (selectSign != null) {
            selectSign.parentNode.removeChild(selectSign);
        }
        var selectGender = createSelect('param', genders);
        param.parentNode.replaceChild(selectGender, param);

    } else if (e.selectedIndex == 2) {
        //User name
        var sign = createSelect('sign', signsName);
        if (selectSign != null) {
            selectSign.parentNode.replaceChild(sign, selectSign);
        } else {
            divOperator.firstChild.appendChild(sign);
        }
        var inputParam = document.createElement("input");
        inputParam.type = "text";
        inputParam.className = "input-xs";
        inputParam.name = "param";
        param.parentNode.replaceChild(inputParam, param);
    } else if (e.selectedIndex == e.length - 1) {
        //DayOfWeek
        if (selectSign != null)selectSign.parentNode.removeChild(selectSign);
        var selectDate = createSelect('param', dates);
        param.parentNode.replaceChild(selectDate, param);
    } else {
        var sign = createSelect('sign', signsDef);
        if (selectSign != null) {
            selectSign.parentNode.replaceChild(sign, selectSign);
        } else {
            divOperator.firstChild.appendChild(sign);
        }
        var inputParam = document.createElement("input");
        inputParam.type = "number";
        inputParam.name = "param";
        param.parentNode.replaceChild(inputParam, param);
    }

};
function split(val) {
    return val.split(/[*-/+]/);
}
function extractLast(term) {
    return split(term).pop();
}


$(document).ready(function () {
    //setup the jtable that will display the results
    policies = $('#policies');
    policies.jtable({
        title: 'Discount policies',
        actions: {
            listAction: window.location.href + '/getAllPolicies',
            createAction: window.location.href + '/add',
            updateAction: window.location.href + '/edit',
            deleteAction: window.location.href + '/delete'
        },
        fields: {
            discountPolicyId: {
                title: 'id',
                key: true,
                list: false,
                create: false,
                edit: false
            }, name: {title: "Name", width: '20%'},
            formula: {title: "discount,%", width: '20%'},
            condition: {title: "Condition", width: '20%',
                input: function (data) {
                    //replace condition's input with add condition button
                    return '<div id="conditionAndButton"><button type="button" ' +
                        'class="btn btn-primary" onclick="addCondition()">Add condition' +
                        '</button><div id="condition" ></div></div>';

                }}, description: {title: 'Description', width: '20%', type: 'textarea'},
            startDate: {title: "Start date", width: '10%', type: 'date'
            },
            endDate: {title: "End date", width: '10%', type: 'date'
            }
        }, formCreated: function (event, data) {
            var availableTags = [];
            var url = "discount_dependency/getAllDependencies";
            $.ajax({
                url: url,
                dataType: "json",
                success: function (data, textStatus) {
                    var values = "";
                    var records = data['Records'];
                    for (var i = 0; i < records.length; i++) {
                        values += records[i]['tag'] + "-" + records[i]['description'] + "<br>";
                        availableTags.push(records[i]['tag']);
                    }
                    values += "For example: age/0.05";
                    var inputFormula = document.getElementsByName("formula")[0];
                    inputFormula.setAttribute("data-original-title", "List of possible shortcuts:");
                    inputFormula.setAttribute("data-content", values);

                }
            });

            var date = data.form.find("input[name='startDate'],input[name='endDate']");
            console.log("Date", date);
            date.attr("readonly", "true");


            var formulaInput = $('input[name="formula"]');
            formulaInput.popover({html: true, trigger: 'focus'});
            formulaInput.autocomplete({
                minLength: 0,
                source: function (request, response) {
                    // delegate back to autocomplete, but extract the last term
                    response($.ui.autocomplete.filter(
                        availableTags, extractLast(request.term)));
                },
                focus: function () {
                    // prevent value inserted on focus
                    return false;
                },
                select: function (event, ui) {
                    var value = this.value;
                    value = value.replace(/[+*/-]/g, "$&,");
                    var terms = value.split(",");
                    // remove the current input
                    terms.pop();
                    // add the selected item
                    terms.push(ui.item.value);
                    // add placeholder to get the comma-and-space at the end
                    terms.push("");
                    this.value = terms.join("");
                    return false;
                }
            });


            if (data.formType == 'edit' && data.record.condition != null) {
                parseCondition(data.record.condition);
            }

        },
        recordAdded: function (event, data) {
            policies.jtable('load');
        },
        recordUpdated: function (event, data) {
            policies.jtable('load');
        }
    })
    ;
    policies.jtable('load');

})
;
/**
 * Parses condition
 * @param condition
 */
function parseCondition(condition) {
    console.log(condition);
    condition = condition.replace(/AND|OR/g, ",$&,").replace(/User's /g, "users.").replace(/ /g, "").replace(/\)|\(/g, "");
    var tokens = condition.split(",");
    console.log(tokens);
    var token;
    for (var i = 0; i < tokens.length;) {
        if (i == 0) {
            addCondition(null, tokens[i]);
            i++;
        } else {
            console.log("i=%s tokens %s \t%s", i, tokens[i], tokens[i + 1]);
            addCondition(tokens[i], tokens[i + 1]);
            i += 2;
        }
    }

};

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
function checkLength(min, max, string) {
    if (string.length < min || string.length > max) {
        return false;
    }
    return true;
}
