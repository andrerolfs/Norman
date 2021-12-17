import sys
import requests

def main():
    argvLength = len(sys.argv)

    # first parameters are keys
    lastKeyIndex = argvLength-5
    background_color = sys.argv[argvLength-4]
    color = sys.argv[argvLength-3]
    value = sys.argv[argvLength-2]
    keycount = sys.argv[argvLength-1]

    parameterMap = {'VALUE': value, 'KEYCOUNT': keycount, 'BACKGROUND_COLOR': background_color, 'COLOR': color}
    # range does not include last element, so +1 is needed
    for i in range(1, lastKeyIndex+1):
        number_str = str(i)
        zero_filled_number = number_str.zfill(2)
        keyName = "KEY" + zero_filled_number
        parameterMap[keyName] = sys.argv[i]

    url = 'http://localhost:8080/'
    x = requests.post(url, data=parameterMap)
    print(parameterMap)
    print(x.text)

# call with python3 norman.py a b c ffffff 000000 v 3
main()

# plain curl example
#curl -d "VALUE=valueOfFirStSecondThird&KEYCOUNT=3&BACKGROUND_COLOR=ff0000&COLOR=000000&KEY01=first&KEY02=second&KEY03=third" -X POST http://localhost:8080