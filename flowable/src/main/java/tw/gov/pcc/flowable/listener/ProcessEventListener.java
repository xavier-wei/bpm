package tw.gov.pcc.flowable.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;

import java.util.Collection;

import static org.flowable.common.engine.api.delegate.event.FlowableEngineEventType.PROCESS_COMPLETED;

public class ProcessEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        if (flowableEvent.getType().equals(PROCESS_COMPLETED)) {
            System.out.println("test");
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public Collection<? extends FlowableEventType> getTypes() {
        return FlowableEventListener.super.getTypes();
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
