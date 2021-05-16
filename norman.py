import sys
import requests

def main():
    argvLength = len(sys.argv)

    keycount = sys.argv[argvLength-1]
    value = sys.argv[argvLength-2]
    lastKeyIndex = argvLength-3

    parameterMap = {'VALUE': value, 'KEYCOUNT': keycount}
    # range does not include last element, so +1 is needed
    for i in range(1, lastKeyIndex+1):
        number_str = str(i)
        zero_filled_number = number_str.zfill(2)
        keyName = "KEY" + zero_filled_number
        parameterMap[keyName] = sys.argv[i]

    url = 'http://localhost:8080/'
    x = requests.post(url, data=parameterMap)
    print(x.text)

# call with python3 norman.py a b c v 3
main()