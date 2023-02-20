/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StateDetailComponent from '@/entities/state/state-details.vue';
import StateClass from '@/entities/state/state-details.component';
import StateService from '@/entities/state/state.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('State Management Detail Component', () => {
    let wrapper: Wrapper<StateClass>;
    let comp: StateClass;
    let stateServiceStub: SinonStubbedInstance<StateService>;

    beforeEach(() => {
      stateServiceStub = sinon.createStubInstance<StateService>(StateService);

      wrapper = shallowMount<StateClass>(StateDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { stateService: () => stateServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundState = { id: 123 };
        stateServiceStub.find.resolves(foundState);

        // WHEN
        comp.retrieveState(123);
        await comp.$nextTick();

        // THEN
        expect(comp.state).toBe(foundState);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundState = { id: 123 };
        stateServiceStub.find.resolves(foundState);

        // WHEN
        comp.beforeRouteEnter({ params: { stateId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.state).toBe(foundState);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
