<#import "../common/dialog_buttons.ftlh" as b/>
<#macro attendeeEditButton>
    <@b.editButton "/attendees/1/edit"/>
</#macro>
<#macro attendeeDeleteButton>
    <@b.deleteButton "/attendees/1"/>
</#macro>
<#macro attendeeCancelButton>
    <@b.cancelButton "/attendees/1/cancel"/>
</#macro>
<#macro attendeeSaveButton>
    <@b.saveButton "/attendees/1"/>
</#macro>