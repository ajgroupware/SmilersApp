package co.smilers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import co.smilers.fragments.BooleanQuestionFragment;
import co.smilers.fragments.FooterBooleanQuestionFragment;
import co.smilers.fragments.GeneralHeaderFragment;
import co.smilers.fragments.GeneralQuestionFragment;
import co.smilers.fragments.QuestionFragment;
import co.smilers.fragments.SelectCampaignFragment;
import co.smilers.fragments.ThanksFragment;
import co.smilers.model.AnswerScore;
import co.smilers.model.QuestionItem;

import static co.smilers.StartZoneActivity.HEADER_FRAGMENT_TAG;
import static co.smilers.StartZoneActivity.QUESTION_FRAGMENT_TAG;
import static co.smilers.StartZoneActivity.THANKS_FRAGMENT_TAG;
import static co.smilers.StartZoneActivity.HEADER_QUESTION_FRAGMENT_TAG;
import static co.smilers.StartZoneActivity.SELECT_FRAGMENT_TAG;

public class NavigationController {

    private final FragmentManager fragmentManager;
    private StartZoneActivity startZoneActivity;

    public NavigationController(StartZoneActivity startZoneActivity) {
        this.fragmentManager = startZoneActivity.getSupportFragmentManager();
        this.startZoneActivity = startZoneActivity;
    }

    public void navigateToQuestion(Long headerquarter, Long zone, Long campaignCode, int size, int current, int indexQuestion, int indexBooleanQuestion) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String questionType = StartZoneActivity.questionItems.get(current).getQuestionType();
        if ("CSAT".equals(questionType)) {
            //AnswerScore answerScore = StartZoneActivity.answerScores.get(indexQuestion);
            QuestionFragment questionFragment = QuestionFragment.newInstance(campaignCode, size, current, indexQuestion, indexBooleanQuestion, headerquarter, zone);
            fragmentTransaction.replace(R.id.fullscreen_content, questionFragment, "QUESTION_FRAGMENT");
            fragmentTransaction.commit();
        } else if ("BOOLEAN".equals(questionType)) {
            BooleanQuestionFragment questionFragment = BooleanQuestionFragment.newInstance(campaignCode, size, current, indexQuestion, indexBooleanQuestion, headerquarter, zone);
            fragmentTransaction.replace(R.id.fullscreen_content, questionFragment, "QUESTION_FRAGMENT");
            fragmentTransaction.commit();
        }


        if (startZoneActivity != null) {
            startZoneActivity.mCurrentFragment = QUESTION_FRAGMENT_TAG;
        }
    }

    public void navigateToThanks(Long headerquarter, Long zone) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ThanksFragment thanksFragment = ThanksFragment.newInstance(headerquarter, zone);
        fragmentTransaction.replace(R.id.fullscreen_content, thanksFragment, "THANKS_FRAGMENT");
        fragmentTransaction.commit();

        if (startZoneActivity != null) {
            startZoneActivity.mCurrentFragment = THANKS_FRAGMENT_TAG;
        }
    }

    public void navigateToHeader(Long headerquarter, Long zone) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GeneralHeaderFragment generalHeaderFragment = GeneralHeaderFragment.newInstance(headerquarter, zone);
        fragmentTransaction.replace(R.id.fullscreen_content, generalHeaderFragment, "HEADER_FRAGMENT");
        fragmentTransaction.commit();

        if (startZoneActivity != null) {
            startZoneActivity.mCurrentFragment = HEADER_FRAGMENT_TAG;
        }
    }

    public void navigateToHeaderQuestion(Long headerquarter, Long zone) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GeneralQuestionFragment generalQuestionFragment = GeneralQuestionFragment.newInstance(headerquarter, zone);
        fragmentTransaction.replace(R.id.fullscreen_content, generalQuestionFragment, "GENERAL_QUESTION");
        fragmentTransaction.commit();

        if (startZoneActivity != null) {
            startZoneActivity.mCurrentFragment = HEADER_QUESTION_FRAGMENT_TAG;
        }
    }

    public void navigateToSelectCampaign(Long headerquarter, Long zone) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectCampaignFragment selectCampaignFragment = SelectCampaignFragment.newInstance(headerquarter, zone);
        fragmentTransaction.replace(R.id.fullscreen_content, selectCampaignFragment, "SELECT_CAMPAIGN");
        fragmentTransaction.commit();

        if (startZoneActivity != null) {
            startZoneActivity.mCurrentFragment = SELECT_FRAGMENT_TAG;
        }

    }

    public void navigateToFooterQuestion(QuestionItem questionItem, Long headerquarter, Long zone) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if ("CSAT".equals(questionItem.getQuestionType())) {

        } else if ("BOOLEAN".equals(questionItem.getQuestionType())) {
            FooterBooleanQuestionFragment questionFragment = FooterBooleanQuestionFragment.newInstance(questionItem.getCampaignCode(), headerquarter, zone);
            fragmentTransaction.replace(R.id.fullscreen_content, questionFragment, "QUESTION_FRAGMENT");
            fragmentTransaction.commit();
        }


        if (startZoneActivity != null) {
            startZoneActivity.mCurrentFragment = QUESTION_FRAGMENT_TAG;
        }
    }
}
