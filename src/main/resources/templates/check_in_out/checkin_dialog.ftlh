<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Check in"/>
<div class="alertDialogContent">
    <div class="primary">Check in <span>${checkin.gameTitle()}</span> for <span>${checkin.attendeeName()} ${checkin.attendeeSurname()}?</span></div>
    <input type="hidden" name="checkoutId" value="${checkin.checkoutId()}"/>
    <button type="submit" hx-post="/checkouts/checkin" hx-swap="beforeend" hx-target=".recentCheckInOuts" onclick="document.getElementById('mainDialog').close()">Check in</button>
    <#if incidental??>
        <div class="secondary">
            <#assign attendee><span>${incidental.attendeeName()} ${incidental.attendeeSurname()}</span></#assign>
            <#if incidental.gameTitles()??>
                <#assign gameTitles><span>${incidental.gameTitles()}</span></#assign>
                ${i18n("did.you.mean.to.check.in.game.for.attendee", gameTitles, attendee)}
            <#else>
                ${i18n("did.you.mean.to.check.out.a.game.for.attendee", attendee)}
            </#if>
            <br/>
            ${i18n("ensure.you.scan.the.games.library.number.first")}
        </div>
    </#if>
</div>