/**
 *  You can modify and use this source freely
 *  only for the development of application related Live2D.
 *
 *  (c) Live2D Inc. All rights reserved.
 */
package com.live2d.yx;

import javax.microedition.khronos.opengles.GL10;

import jp.live2d.Live2D;
import jp.live2d.framework.L2DViewMatrix;
import jp.live2d.framework.Live2DFramework;
import android.app.Activity;
import android.util.Log;


public class LAppLive2DManager
{
	
	static public final String 	TAG = "SampleLive2DManager";

	private LAppView 				view;						

	public LAppModel mModel;


	
	private int 					modelCount		=-1;
	private boolean 				reloadFlg = true;



	public LAppLive2DManager()
	{
		Live2D.init();
		Live2DFramework.setPlatformManager(new PlatformManager());
	}


	public void releaseModel()
	{
		if (mModel != null) {
			mModel.release();
		}
	}


	
	public void update(GL10 gl)
	{
		view.update();
		if(reloadFlg)
		{
			reloadFlg=false;
			try {
				releaseModel();
				mModel = new LAppModel();
				mModel.load(gl, LAppDefine.MODEL_HARU);
				mModel.feedIn();
			} catch (Exception e) {
				Log.e(TAG,"Failed to load."+e.getStackTrace());
			}
		}
	}





	public LAppModel getModel() {
		return mModel;
	}


	public int getModelNum()
	{
		if (mModel != null) {
			return 1;
		} else {
			return 0;
		}
	}


	//=========================================================
	
	//=========================================================
	
	public LAppView  createView(Activity act)
	{

		view = new LAppView( act ) ;
		view.setLive2DManager(this);
		view.startAccel(act);
		return view ;
	}


	
	public void onResume()
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "onResume");
		view.onResume();
	}


	
	public void onPause()
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "onPause");
		view.onPause();
	}


	
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "onSurfaceChanged "+width+" "+height);
		view.setupView(width,height);

		System.out.println("日志：执行了3");
	}


	//=========================================================
	
	//=========================================================

	//=========================================================
	
	//=========================================================
	
	public boolean tapEvent(float x,float y)
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "tapEvent view x:"+x+" y:"+y);

		if (mModel.hitTest(LAppDefine.HIT_AREA_HEAD, x, y)) {
			if (LAppDefine.DEBUG_LOG)
				Log.d(TAG, "Tap face");
			mModel.setRandomExpression();
		} else if (mModel.hitTest(LAppDefine.HIT_AREA_BODY, x, y)) {
			if (LAppDefine.DEBUG_LOG)
				Log.d(TAG, "Tap body");
			mModel.startRandomMotion(LAppDefine.MOTION_GROUP_FLICK_HEAD, LAppDefine.PRIORITY_NORMAL);
		}

		return true;
	}


	
	public void flickEvent(float x,float y)
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "flick x:"+x+" y:"+y);

		if (mModel.hitTest(LAppDefine.HIT_AREA_HEAD, x, y)) {
			if(LAppDefine.DEBUG_LOG)
				Log.d(TAG, "Flick head.");
			mModel.startRandomMotion(LAppDefine.MOTION_GROUP_FLICK_HEAD, LAppDefine.PRIORITY_NORMAL);
		}

	}


	
	public void maxScaleEvent()
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "Max scale_in event.");

		mModel.startRandomMotion(LAppDefine.MOTION_GROUP_FLICK_HEAD, LAppDefine.PRIORITY_NORMAL);
	}


	
	public void minScaleEvent()
	{
		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "Min scale_in event.");

		mModel.startRandomMotion(LAppDefine.MOTION_GROUP_FLICK_HEAD, LAppDefine.PRIORITY_NORMAL);
	}



//	public void shakeEvent()
//	{
//
//		if(LAppDefine.DEBUG_LOG)Log.d(TAG, "Shake event.");
//
//		System.out.println("日志：执行了10");
//		for (int i=0; i<models.size(); i++)
//		{
//			models.get(i).startRandomMotion(LAppDefine.MOTION_GROUP_SHAKE,LAppDefine.PRIORITY_FORCE );
//		}
//	}


	public void setAccel(float x,float y,float z)
	{
		if (mModel != null) {
			mModel.setAccel(x, y, z);
		}
	}


	public void setDrag(float x,float y)
	{
		if (mModel != null) {
			mModel.setDrag(x, y);
		}
	}


	public L2DViewMatrix getViewMatrix()
	{
		return view.getViewMatrix();
	}
}
