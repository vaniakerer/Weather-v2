package com.example.ivan.weatherapp.utils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.renderscript.Sampler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.ivan.weatherapp.R;

/**
 * Created by ivan
 */

public class CustomAnimationUtils {
    private static final int DEFAULT_DURATION = 200;
    private static final int SMALL_DURATION = 20;

    public void animateIn(View view) {

        Animation scaledAnimation = getScaleAnimation(view);
        Animation alphaAnimation = getAlphaAnimation();

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaledAnimation);
        animationSet.addAnimation(alphaAnimation);

        view.startAnimation(animationSet);
    }

    public void animateInFromXml(View view) {
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), R.animator.anim_xml);
        animatorSet.setTarget(view);
        animatorSet.start();
    }

    private Animation getScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0F, 1.2F, 0F, 1.2F,
                0F, view.getMeasuredHeight());
        anim.setDuration(DEFAULT_DURATION);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setFillAfter(true);

        return anim;
    }

    private Animation getScaledBackAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(1.2F, 1F, 1.2F, 1F);
        anim.setDuration(SMALL_DURATION);
        anim.setFillAfter(true);


        return anim;
    }

    private Animation getAlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0F, 1F);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setFillAfter(true);

        return alphaAnimation;
    }

    public void animate(View imageView) {
        final AnimatorSet set = new AnimatorSet();
        AnimatorSet moveLeftSet = new AnimatorSet(); //move left
        ObjectAnimator move = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -32.0f, 122);
        move.setRepeatCount(1);
        ObjectAnimator show = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.3f, 1f);//hide
        show.setRepeatCount(1);
        moveLeftSet.setDuration(500).playTogether(move, show);
        set.playSequentially(moveLeftSet);
        set.start();
    }

    public void animageInfl(View view) {
       /* AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator showAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.3f, 1f);
        ObjectAnimator hideAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0.3f);

        showAnimator.setRepeatCount(ValueAnimator.INFINITE);
        hideAnimator.setRepeatCount(ValueAnimator.INFINITE);

        hideAnimator.setStartDelay(1000);

        showAnimator.setDuration(1000);
        hideAnimator.setDuration(1000);

        animatorSet.play(hideAnimator).before(showAnimator);
        animatorSet.setDuration(2000);
        animatorSet.start();
*/

  /*      ObjectAnimator xAnim = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 500f);
        ObjectAnimator yAnim = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, 500f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xAnim).with(yAnim);
        animatorSet.setDuration(500);

        animatorSet.start();*/

        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.7f, 1.4f);
        Keyframe kf2 = Keyframe.ofFloat(0.9f, 0.9f);
        Keyframe kf3 = Keyframe.ofFloat(1f, 1f);

        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation);
        rotationAnim.setDuration(400);
        rotationAnim.start();

        /*PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0, -50);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.5f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(giftFrameLayout, translationY, alpha);
        animator.setStartDelay(0);
        animator.setDuration(1500);

        translationY = PropertyValuesHolder.ofFloat("translationY", -50, -100);
        alpha = PropertyValuesHolder.ofFloat("alpha", 0.5f, 0f);
        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(giftFrameLayout, translationY, alpha);
        animator1.setStartDelay(0);
        animator1.setDuration(1500);


//        ObjectAnimator fadeAnimator2 = GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 0, 0, 0, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).after(animator);
//        animatorSet.play(fadeAnimator2).after(animator1);
        animatorSet.start();*/
    }
}
