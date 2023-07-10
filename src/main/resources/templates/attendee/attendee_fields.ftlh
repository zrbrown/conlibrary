<#import "attendee_dialog_buttons.ftlh" as b/>
<span hx-swap-oob="outerHtml:.dialogTitle">${attendee.name()} ${attendee.surname()} (${attendee.pronouns()})</span>
<div hx-swap-oob="outerHtml:.dialogHeaderButtons">
    <@b.attendeeEditButton attendee.id()/>
    <@b.attendeeDeleteButton attendee.id()/>
</div>
<div class="attendeeInfo" hx-swap-oob="outerHtml:.attendeeInfo">
    <div class="attendeeBadgeId">Badge #${attendee.badgeId()}</div>
</div>