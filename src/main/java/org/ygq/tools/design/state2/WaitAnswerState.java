package org.ygq.tools.design.state2;

public class WaitAnswerState extends State {

    @Override
    public Builder getBuilder() {
        return new State.Builder()
                .setReminderVisible(true)
                .setModifyMsgVisible(true)
                .setAnswerState(mOrderContext.getContext().getResources().getString(R.string.answer_ask_question_status_wait_answer))
                .build();
    }
}
