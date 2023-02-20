/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StateComponent from '@/entities/state/state.vue';
import StateClass from '@/entities/state/state.component';
import StateService from '@/entities/state/state.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('State Management Component', () => {
    let wrapper: Wrapper<StateClass>;
    let comp: StateClass;
    let stateServiceStub: SinonStubbedInstance<StateService>;

    beforeEach(() => {
      stateServiceStub = sinon.createStubInstance<StateService>(StateService);
      stateServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StateClass>(StateComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          stateService: () => stateServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      stateServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStates();
      await comp.$nextTick();

      // THEN
      expect(stateServiceStub.retrieve.called).toBeTruthy();
      expect(comp.states[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      stateServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(stateServiceStub.retrieve.callCount).toEqual(1);

      comp.removeState();
      await comp.$nextTick();

      // THEN
      expect(stateServiceStub.delete.called).toBeTruthy();
      expect(stateServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
