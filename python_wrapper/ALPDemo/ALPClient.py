# -*- coding: utf-8 -*-
import argparse
import os
from os.path import dirname
import subprocess
from functools import reduce
from operator import add
import urllib.parse
from urllib.parse import unquote
import re


class ALPClient:

    """An interface to the ALP."""

    ALP_BASEDIR = "./ALPDemo"

 

    def __init__(self):

        self._classpath = self.ALP_BASEDIR

    def processText(self, texts):

        """Processes a batch of texts, and returns
        a list containing the list of tokens from each text.

 

        Arguments:

        texts - list of texts to process

        """
        
        texts1 = urllib.parse.quote(texts)
        command = ['java']

        command.extend(['-jar'])

        command.extend(['ALPClient.jar'])

        command.extend(["-"+texts1])

 

        # Create the subprocess

        sub = subprocess.Popen(command,

                               stdin=subprocess.PIPE,

                               stdout=subprocess.PIPE,

                               stderr=subprocess.PIPE,

                               cwd=self._classpath)



        # Read the output.

        tokenize_out, tokenize_err = sub.communicate()
        print(tokenize_err)
        tokenized_items = tokenize_out

        return unquote(tokenized_items)
def main():

                parser = argparse.ArgumentParser(

        description="ALP wrapper.")

                parser.add_argument("-tIn", "--textIn", help="text to be processed")

                args = parser.parse_args()

                if not args.textIn:

                               parser.error('Input text must be specified.')

 



    #ALP

                alp = ALPClient ()
                
                res = alp.processText(args.textIn)

                print (res.replace("#"," "))

       

 

if __name__ == '__main__':

    main()
