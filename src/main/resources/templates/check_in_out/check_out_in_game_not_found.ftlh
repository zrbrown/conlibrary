<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Game not found" "#ab0000"/>
<div class="alertDialogContent">
    <div class="primary">
        <#assign libraryId><span>${gameScanInput}</span></#assign>
        ${i18n("library.number.number.does.not.exist", libraryId)}
    </div>
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