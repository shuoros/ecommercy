import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import * as sinon from 'sinon';
import { Subject, Subscription } from 'rxjs';

import * as config from '@/shared/config/config';
import JhiTracker from '@/admin/tracker/tracker.vue';
import JhiTrackerComponent from '@/admin/tracker/tracker.component';
import TrackerService from '@/admin/tracker/tracker.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', FontAwesomeIcon);

const axiosStub = {
  get: sinon.stub(axios, 'get'),
};

describe('JhiTracker', () => {
  let wrapper: Wrapper<JhiTrackerComponent>;
  let trackerView: JhiTrackerComponent;

  let subject: Subject<any>;
  let subscription: Subscription;
  let subscriptionSpy;
  let trackerServiceStub: any;

  beforeEach(() => {
    axiosStub.get.resolves({ data: {} });
    subject = new Subject<any>();
    trackerServiceStub = sinon.createStubInstance<TrackerService>(TrackerService);
    trackerServiceStub.subscribe = sinon.stub().callsFake(observer => {
      subscription = subject.subscribe(observer);
      subscriptionSpy = sinon.spy(subscription, 'unsubscribe');
      return subscription;
    });

    wrapper = shallowMount<JhiTrackerComponent>(JhiTracker, {
      store,
      i18n,
      localVue,
      provide: {
        trackerService: () => trackerServiceStub,
      },
    });
    trackerView = wrapper.vm;
  });

  it('should subscribe', () => {
    expect(trackerServiceStub.subscribe.calledOnce).toBeTruthy();
  });

  it('should unsubscribe at destroy', () => {
    // WHEN
    wrapper.destroy();

    // THEN
    expect(subscriptionSpy.calledOnce).toBeTruthy();
  });

  it('should add new activity', () => {
    // GIVEN
    const activity1 = { time: '2020-01-01', page: 'login', sessionId: '123' };
    trackerView.activities = [activity1];

    // WHEN
    const activity2 = { time: '2020-01-01', page: 'login', sessionId: '456' };
    subject.next(activity2);

    // THEN
    expect(trackerView.activities).toEqual([activity1, activity2]);
  });

  it('should not add logout activity', () => {
    // WHEN
    subject.next({ time: '2020-01-01', page: 'logout', sessionId: '123' });

    // THEN
    expect(trackerView.activities).toEqual([]);
  });

  it('should update user activity', () => {
    // GIVEN
    trackerView.activities = [{ time: '2020-01-01', page: 'login', sessionId: '123' }];

    // WHEN
    const activity = { time: '2020-01-01', page: 'login', sessionId: '123' };
    subject.next(activity);

    // THEN
    expect(trackerView.activities).toEqual([activity]);
  });

  it('should remove user activity', () => {
    // GIVEN
    trackerView.activities = [{ time: '2020-01-01', page: 'login', sessionId: '123' }];

    // WHEN
    subject.next({ time: '2020-01-01', page: 'logout', sessionId: '123' });

    // THEN
    expect(trackerView.activities).toEqual([]);
  });
});
