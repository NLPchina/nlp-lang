/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.nlpcn.commons.lang.chardet;
import java.lang.* ;

public class nsEUCSampler {

	int mTotal = 0;
	int mThreshold = 200 ;
	int mState = 0;
	public int mFirstByteCnt[] = new int[94] ;
	public int mSecondByteCnt[] = new int[94] ;
	public float mFirstByteFreq[] = new float[94] ;
	public float mSecondByteFreq[] = new float[94];

	public nsEUCSampler() {
		Reset() ;
	}

	public void Reset() {
		mTotal = 0 ;
		mState = 0 ;
		for(int i=0; i<94; i++)
			mFirstByteCnt[i] = mSecondByteCnt[i] = 0 ;
	}

	boolean EnoughData() { return mTotal > mThreshold; }

        boolean GetSomeData() { return mTotal > 1; }

        boolean Sample(byte[] aIn, int aLen) {

           if(mState == 1)
               return false;

           int p = 0;

           // if(aLen + mTotal > 0x80000000) 
           //    aLen = 0x80000000 - mTotal;

           int i;
           for(i=0; (i<aLen) && (1 != mState) ;i++,p++)
           {
               switch(mState) {
                  case 0:
                    if( ( aIn[p] & 0x0080 ) != 0 )  
                    {
                       if((0xff==(0xff&aIn[p])) || ( 0xa1>(0xff&aIn[p]))) {
                          mState = 1;
                       } else {
                          mTotal++;
                          mFirstByteCnt[(0xff&aIn[p]) - 0xa1]++;
                          mState = 2;
                       }
                    }
                    break;
                  case 1:
                    break;
                  case 2:
                    if( (aIn[p] & 0x0080)  != 0 )
                    {
                       if((0xff == (0xff&aIn[p])) 
				|| ( 0xa1 > (0xff&aIn[p]))) {
                          mState = 1;
                       } else {
                          mTotal++;
                          mSecondByteCnt[(0xff&aIn[p]) - 0xa1]++;
                          mState = 0;
                       }
                    } else {
                       mState = 1;
                    }
                    break;
                  default:
                    mState = 1;
               }
            }
            return (1 != mState  );
        }


        void CalFreq() {
	   for(int i = 0 ; i < 94; i++) {
	      mFirstByteFreq[i] = (float)mFirstByteCnt[i] / (float)mTotal;
	      mSecondByteFreq[i] = (float)mSecondByteCnt[i] / (float)mTotal;
	   }
	}

        float   GetScore(float[] aFirstByteFreq, float aFirstByteWeight,
                         float[] aSecondByteFreq, float aSecondByteWeight)
	{
       	   return aFirstByteWeight * GetScore(aFirstByteFreq, mFirstByteFreq) +
              aSecondByteWeight * GetScore(aSecondByteFreq, mSecondByteFreq);
	}

        float   GetScore(float[] array1, float[] array2) {
           float s;
           float sum=0.0f;

       	   for(int i=0;i<94;i++) {
              s = array1[i] - array2[i];
              sum += s * s;
           }
           return (float) Math.sqrt((double)sum) / 94.0f;
	}
}


