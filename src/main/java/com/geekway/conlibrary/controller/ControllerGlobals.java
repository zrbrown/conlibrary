package com.geekway.conlibrary.controller;

import freemarker.core.HTMLOutputFormat;
import freemarker.core.TemplateHTMLOutputModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

@ControllerAdvice
public class ControllerGlobals {

    @ModelAttribute
    public void addAttributes(@RequestHeader(value = "HX-Request", required = false, defaultValue = "false") Boolean isHtmx,
                              Model model) {
        model.addAttribute("isHtmx", isHtmx);

        model.addAttribute("i18n", (TemplateMethodModelEx) arguments -> {
            if (arguments.get(0) instanceof TemplateScalarModel key) {
                ResourceBundle bundle = ResourceBundle.getBundle("i18n/i18n", Locale.US);
                Stream<?> args = arguments.subList(1, arguments.size()).stream();
                return HTMLOutputFormat.INSTANCE.fromMarkup(
                        MessageFormat.format(HTMLOutputFormat.INSTANCE.escapePlainText(bundle.getString(key.getAsString())),
                                args.map(arg -> switch (arg) {
                                    case TemplateScalarModel str -> {
                                        try {
                                            yield str.getAsString();
                                        } catch (TemplateModelException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    case TemplateHTMLOutputModel html -> {
                                        try {
                                            yield HTMLOutputFormat.INSTANCE.getMarkupString(html);
                                        } catch (TemplateModelException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    default -> throw new IllegalStateException("Unexpected value: " + arg);
                                }).toArray()));
            }
            throw new RuntimeException();
        });
    }
}
