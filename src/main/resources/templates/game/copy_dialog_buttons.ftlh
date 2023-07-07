<#import "../common/dialog_buttons.ftlh" as b/>
<#macro copyEditButton>
    <@b.editButton "/games/copies/${copy.id()?c}/edit"/>
</#macro>
<#macro copyDeleteButton>
    <@b.deleteButton "/games/copies/${copy.id()?c}"/>
</#macro>
<#macro copyCancelButton>
    <@b.cancelButton "/games/copies/${copy.id()?c}/cancel"/>
</#macro>
<#macro copySaveButton>
    <@b.saveButton "/games/copies/${copy.id()?c}"/>
</#macro>