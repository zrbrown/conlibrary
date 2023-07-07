<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Game not found" "#ab0000"/>
<div class="alertDialogContent">
    <#--        IF IT'S A BADGE ID AND THAT PERSON HAS A GAME CHECKED OUT-->
    <div class="primary">
        <#assign libraryId><span>${checkin.libraryId()}</span></#assign>
        ${i18n("library.number.number.does.not.exist", libraryId)}
    </div>
    <#if incidental??>
        <div class="secondary">
            <#assign gameTitle><span>${incidental.gameTitle()}</span></#assign>
            <#assign borrower><span>${incidental.borrower()}</span></#assign>
            ${i18n("did.you.mean.to.check.out.game.to.borrower", gameTitle, borrower)}
            <br/>
            ${i18n("ensure.you.scan.the.games.library.number.first")}
        </div>
    </#if>
</div>