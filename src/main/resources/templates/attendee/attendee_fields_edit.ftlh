<#import "attendee_dialog_buttons.ftlh" as b/>
<div hx-swap-oob="outerHtml:.dialogHeaderButtons">
    <@b.attendeeCancelButton attendee.id()/>
    <@b.attendeeSaveButton attendee.id()/>
</div>
<div id="attendeeInfo" class="attendeeInfo" hx-swap-oob="outerHtml:.attendeeInfo">
    <label for="">First Name</label>
    <input type="text" name="name" value="${attendee.name()}"/>
    <label for="">Last Name</label>
    <input type="text" name="surname" value="${attendee.surname()}"/>
    <label for="">Pronouns</label>
    <input type="text" name="pronouns" value="${attendee.pronouns()}"/>
    <label for="">Badge #</label>
    <input type="text" name="badgeId" value="${attendee.badgeId()}"/>
</div>