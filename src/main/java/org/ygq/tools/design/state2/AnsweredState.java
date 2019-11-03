package org.ygq.tools.design.state2;

public class AnsweredState extends State {

	@Override
	public Builder getBuilder() {
		QuestionListModel.ListModel itemModel = mOrderContext.getModel();

		CharSequence answerState = getAnswerState(itemModel);
		return new State.Builder().setHasAnswer(true).setAnswerState(answerState)
				.setAskAgainVisible(showAskAgain(itemModel)).setEvaluateVisible(!itemModel.getIsEvaluate())
				.setReConsultVisible(itemModel.getIsEvaluate()).build();
	}

	private CharSequence getAnswerState(QuestionListModel.ListModel itemModel) {
		// 一些判断获取显示字段
	}
}
