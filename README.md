# GCCToTC
#### A Converter for projects done in gcc to make them more turbo c "friendly"
We often love to code in gcc using vim or any other editor for their flexibilty, and responsiveness. I avoided TC not only for that, but for its "boring" and eye straining UI.
So I decided to come up with a solution which will bond a bridge between the two. conio.h isn't included in gcc. People have to use library like n-curses to do that. 
And that is needed in TC very much for the good ol' clrscr() while startup and getch() while termination. Meanwhile TC doesn't
permit to use void return type of main, and gcc does. And all of the things included but not limited to the mentioned,
is taken care by this program. This programs takes any c file as input, and prints it's "TC compaitble" version in a new file
suffixed by "_tc" in the same folder. It is a simple java source file. You can modify it to fine tune your choices, or go by the 
defaults by just using a "java AnalyzeFileAndConvert.java [file_name]". It is still primitve. But it's worth a shot. Because it's
the simpliest. ;)

#### Happy Coding :)
