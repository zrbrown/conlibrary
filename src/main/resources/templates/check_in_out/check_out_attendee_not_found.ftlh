<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Attendee not found" "#ab0000"/>
<div class="alertDialogContent">
    <div class="primary">
        Attendee #<span>${badgeScanInput}</span> does not exist.
    </div>
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