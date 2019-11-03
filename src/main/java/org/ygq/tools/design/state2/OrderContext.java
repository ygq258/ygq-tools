package org.ygq.tools.design.state2;

public class OrderContext {
	//我这里就没声明是静态常量了
	    public  State waitAnswerState = new WaitAnswerState();
	    public  State answeredState = new AnsweredState();
	    public  State overTimeState = new OverTimeState();
	    public  State rejectedState = new RejectedState();
	    private State currentState;
	    private QuestionListModel.ListModel model;
	    private Context context;
	    private QuestionTypeEnum questionTypeEnum;

	    public Context getContext() {
	        return context;
	    }

	    public QuestionListModel.ListModel getModel() {
	        return model;
	    }

	    public QuestionTypeEnum getQuestionTypeEnum() {
	        return questionTypeEnum;
	    }

	    public void setCurrentState(Context context, QuestionListModel.ListModel model, QuestionTypeEnum questionTypeEnum) {
	        this.model = model;
	        this.context = context;
	        this.questionTypeEnum = questionTypeEnum;
	        State state = null;
	        //0已提问，1已回答，2已过期 3已拒单
	        switch (model.getStatus()) {
	            case 0:
	                state = waitAnswerState;
	                break;
	            case 1:
	                state = answeredState;
	                break;
	            case 2:
	                state = overTimeState;
	                break;
	            case 3:
	                state = rejectedState;
	                break;
	        }
	        setCurrentState(state);
	    }

	    public void setCurrentState(State state) {
	        currentState = state;
	        currentState.setmOrderContext(this);
	    }

	    public State.Builder getBuilder() {
	        return this.currentState.getBuilder();
	    }
	}
