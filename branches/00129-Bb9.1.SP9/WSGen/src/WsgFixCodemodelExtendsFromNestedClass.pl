#!/usr/local/bin/perl

#http://rosettacode.org/wiki/Walk_a_directory/Recursively
#http://stackoverflow.com/questions/2476019/how-can-i-recursively-read-out-directories-in-perl
#http://stackoverflow.com/questions/6579495/how-to-concatenate-pathname-and-relative-pathname
#http://stackoverflow.com/questions/15263987/perl-directory-concatenation

use strict;
use warnings;
use File::Find qw(find);
use Cwd;
use URI;

my $pwd = cwd()."/";
my $dir = $ARGV[0];
my $pattern = '.*java';
#find sub {print $File::Find::name if /$pattern/}, $dir;
find sub {removeExtraSpaceFromExtendsFromNestedClass($File::Find::name) if /$pattern/}, $dir;

sub removeExtraSpaceFromExtendsFromNestedClass {
	undef $/;
	my ($f_path) = @_;
	#print $pwd."\n";
	my $f_absuri = URI->new($f_path)->abs($pwd);
	print $f_absuri."\n";
	
	open(INFI, "<", $f_absuri) || die("can't open ".$f_absuri.": $!");
	my $data = <INFI>;
	$data =~ s/^(\s*)extends\s+([^\s]+)\s\.([^\s]+)\s*$/$1extends $2.$3/gxm;
	close INFI;
	open(OUTF, ">", $f_absuri) || die("can't open ".$f_absuri.": $!");
	print OUTF $data;
}

