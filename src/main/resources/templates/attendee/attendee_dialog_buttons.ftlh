<#import "../common/dialog_buttons.ftlh" as b/>
<#macro attendeeEditButton attendeeId>
    <@b.editButton "/attendees/${attendeeId}/edit"/>
</#macro>
<#macro attendeeDeleteButton attendeeId>
    <@b.deleteButton "/attendees/${attendeeId}"/>
</#macro>
<#macro attendeeCancelButton attendeeId>
    <@b.cancelButton "/attendees/${attendeeId}/cancel"/>
</#macro>
<#macro attendeeSaveButton attendeeId>
    <@b.saveButton "/attendees/${attendeeId}"/>
</#macro>