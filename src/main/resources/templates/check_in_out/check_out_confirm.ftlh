<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Check out ${checkout.gameCopy().title()}"/>
<div class="alertDialogContent">
    <div class="primary">
        Check out <span>${checkout.gameCopy().title()}</span> to <span>${checkout.attendee().name()}</span>?
    </div>
    <input type="hidden" name="libraryCopyId" value="${checkout.gameCopy().id()}"/>
    <input type="hidden" name="attendeeId" value="${checkout.attendee().id()}"/>
    <#if checkout.attendee().currentCheckouts()??>
        <div class="secondary">
            ${checkout.attendee().name()} already has ${checkout.attendee().currentCheckoutCount()} game<#if checkout.attendee().currentCheckoutCount() != 1>s</#if> checked out:
            <br/>
            <b>${checkout.attendee().currentCheckouts()}</b>
            <br/>
            <div class="labeledCheckbox">
                <input id="checkoutOverrideCheckbox" name="overrideLimit" type="checkbox"/>
                <label for="checkoutOverrideCheckbox">Override checkout limit</label>
            </div>
        </div>
    </#if>
    <button type="submit" hx-post="/checkouts/checkout" hx-swap="beforeend" hx-target=".recentCheckInOuts" onclick="document.getElementById('mainDialog').close()">Check out</button>
    <#if incidental??>
        <div class="secondary">
            <#assign gameTitles><span>${incidental.gameTitles()}</span></#assign>
            <#if incidental.attendeeName()??>
                <#assign attendee><span>${incidental.attendeeName()} ${incidental.attendeeSurname()}</span></#assign>
                ${i18n("did.you.mean.to.check.in.game.for.attendee", gameTitles, attendee)}
            <#else>
                ${i18n("did.you.mean.to.check.out.game", gameTitles)}
            </#if>
            <br/>
            ${i18n("ensure.you.scan.the.games.library.number.first")}
        </div>
    </#if>
</div>
