<#import "../common/dialog_header.ftlh" as h/>
<@h.header "Check in"/>
<div class="alertDialogContent">
    <div class="primary">Check in <span>${checkin.gameTitle()}</span> for <span>${checkin.borrower()}</span></div>
<#--    IF IT'S A BADGE ID AND THAT PERSON HAS A GAME CHECKED OUT-->
    <#if incidental??>
        <div class="secondary">
            (Did you mean to check in <span>${incidental.gameTitle()}</span> for <span>${incidental.borrower()}</span>?)
            <br/>
            Make sure you scan the game's Library # first!
        </div>
    </#if>
</div>