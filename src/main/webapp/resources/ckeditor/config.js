/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */
CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here.
    // For the complete reference:
    // http://docs.ckeditor.com/#!/api/CKEDITOR.config
    config.autoParagraph = false;
    config.extraPlugins = 'smiley,bbcode';
    config.skin = 'kama';
    // The toolbar groups arrangement, optimized for two toolbar rows.
    config.toolbarGroups = [
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
        { name: 'links' },
        { name: 'insert' },
        { name: 'forms' },
        { name: 'tools' },
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'others' },
        '/',
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
        { name: 'styles' },
        { name: 'colors' },
        { name: 'about' }


    ];
    config.smiley_path = CKEDITOR.basePath + 'plugins/smiley/images/skype/';
    config.smiley_descriptions = ["(angel)", ":@", "(hug)", "(beer)", "(blush)", "(bow)", "(punch)", "(u)", "(^)", "(call)", "(cash)", "(mp)", "(clap)", "(coffee)", "8-)", ";(", "(dance)", "(devil)", "(doh)", "(d)", "|-(", "(emo)", "]:)", "(flex)", "(F)", "(chuckle)", "(handshake)", "(happy)", "(h)", "(wave)", "(inlove)", "(wasntme)", "(envy)", ":*", ":D", "(e)", "(makeup)", "(mm)", "(~)", "(music)", "8-|", "(ninja)", "(n)", "(nod)", ":x", "(party)", "(pi)", "(puke)", "(rain)", "(rofl)", ":(", "(shake)", "(skype)", "|-)", ":)", "(smirk)", ":-|", "(*)", "(sun)", "(sweat)", "(talk)", "(think)", "(o)", "(yawn)", ":p", "(wait)", "(whew)", ";)", ":^)", ":S", "(y)", "ASCII text", "(bandit)", "(bug)", "(drunk)", "(finger)", "(fubar)", "(headbang)", "(mooning)", "(poolparty)", "(rock)", "(smoking) or (ci)", "(heidy)", "(swear)", "(tmi)", "(toivo)"]
    config.smiley_images = ["angel.gif", "angry.gif", "bearhug.gif", "beer.gif", "blush.gif", "bow.gif", "boxing.gif", "brokenheart.gif", "cake.gif", "callme.gif", "cash.gif", "cellphone.gif", "clapping.gif", "coffee.gif", "cool.gif", "crying.gif", "dance.gif", "devil.gif", "doh.gif", "drink.gif", "dull.gif", "emo.gif", "evilgrin.gif", "flex.gif", "flower.gif", "giggle.gif", "handshake.gif", "happy.gif", "heart.gif", "hi.gif", "inlove.gif", "itwasntme.gif", "jealous.gif", "kiss.gif", "laughing.gif", "mail.gif", "makeup.gif", "mmm.gif", "movie.gif", "music.gif", "nerd.gif", "ninja.gif", "no.gif", "nod.gif", "nospeak.gif", "party.gif", "pizza.gif", "puke.gif", "rain.gif", "rofl.gif", "sad.gif", "shakeno.gif", "skype.gif", "sleepy.gif", "smile.gif", "smirk.gif", "speechless.gif", "star.gif", "sun.gif", "sweating.gif", "talking.gif", "thinking.gif", "time.gif", "tired.gif", "tongue out.gif", "wait.gif", "whew.gif", "wink.gif", "wondering.gif", "worried.gif", "yes.gif", "hiddenbandit.gif", "hiddenbug.gif", "hiddendrunk.gif", "hiddenfinger.gif", "hiddenfubar.gif", "hiddenheadbang.gif", "hiddenmooning.gif", "hiddenpoolparty.gif", "hiddenrockout.gif", "hiddensmoking.gif", "hiddensquirrell.gif", "hiddenswear.gif", "hiddentmi.gif", "hiddentoivo.gif"];


    // Remove some buttons, provided by the standard plugins, which we don't
    // need to have in the Standard(s) toolbar.
    config.removeButtons = 'Underline,Subscript,Superscript';

    // Se the most common block elements.
    config.format_tags = 'p;h1;h2;h3;pre';

    // Make dialogs simpler.
    config.removeDialogTabs = 'image:advanced;link:advanced';
};
