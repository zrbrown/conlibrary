<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Check out ${checkout.gameCopy().title()}"/>
<div class="alertDialogContent">
    <div class="primary">
        Check out <span>${checkout.gameCopy().title()}</span> to <span>${checkout.borrower().name()}</span>?
    </div>
    <#if checkout.borrower().currentCheckouts()?size gt 0>
        <div class="secondary">
            ${checkout.borrower().name()} already has ${checkout.borrower().currentCheckouts()?size} games checked out.
            <br/>
            (<#list checkout.borrower().currentCheckouts() as game>${game.title()}<#sep>, </#sep></#list>)
            <br/>
            <label for="checkoutOverrideCheckbox">Override checkout limit</label>
            <input id="checkoutOverrideCheckbox" type="checkbox"/>
        </div>
    </#if>
    <button type="button">Check out</button>
    <#if incidentalAttendee?? && !incidentalGame??>
        <div class="secondary">
            Did you mean to check out to <span>${incidentalAttendee}?</span>
        </div>
    </#if>
    <#if incidentalGame?? && !incidentalAttendee??>
        <div class="secondary">
            Did you mean to check out <span>${incidentalGame}?</span>
        </div>
    </#if>
    <#if incidentalGame?? && incidentalAttendee??>
        <div class="secondary">
            Did you mean to check out <span>${incidentalGame}</span> to <span>${incidentalAttendee}?</span>
        </div>
    </#if>
    <#if incidentalAttendee?? || incidentalGame??>
        <div class="secondary">
            Ensure you scan the game's Library # first!
        </div>
    </#if>
</div>
