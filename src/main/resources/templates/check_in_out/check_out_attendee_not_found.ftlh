<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Attendee not found"/>
<div class="alertDialogContent">
    <div class="primary">
        Attendee #<span>${attendeeId}</span> does not exist.
    </div>
    <#if incidentalGame??>
        <div class="secondary">
            Did you mean to check out <span>${incidentalGame}</span>?
            <br/>
            Ensure you scan the game's Library # first!
        </div>
    </#if>
</div>