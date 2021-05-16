#!/bin/sh

echo Norman POST request

argv=("$@")

keycount=`expr $# '-' 1`
echo keycount ${argv[keycount]}

value=`expr $# '-' 2`
echo value ${argv[value]}

lastkey=`expr $# '-' 3`
echo key array range is 0..$lastkey

parameterlist="VALUE=${argv[value]}"

for (( j=0; j<=lastkey; j++ )); do
    parameterlist=$(printf "$parameterlist&KEY%02d=%s" `expr $j '+' 1` "${argv[j]}")
done

parameterlist=$(printf "$parameterlist&KEYCOUNT=%s" "${argv[keycount]}")
echo "$parameterlist"

curl -X POST -d $parameterlist http://localhost:8080/

