/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StateUpdateComponent from '@/entities/state/state-update.vue';
import StateClass from '@/entities/state/state-update.component';
import StateService from '@/entities/state/state.service';

import CityService from '@/entities/city/city.service';

import CountryService from '@/entities/country/country.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('State Management Update Component', () => {
    let wrapper: Wrapper<StateClass>;
    let comp: StateClass;
    let stateServiceStub: SinonStubbedInstance<StateService>;

    beforeEach(() => {
      stateServiceStub = sinon.createStubInstance<StateService>(StateService);

      wrapper = shallowMount<StateClass>(StateUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          stateService: () => stateServiceStub,
          alertService: () => new AlertService(),

          cityService: () =>
            sinon.createStubInstance<CityService>(CityService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          countryService: () =>
            sinon.createStubInstance<CountryService>(CountryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.state = entity;
        stateServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stateServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.state = entity;
        stateServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stateServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundState = { id: 123 };
        stateServiceStub.find.resolves(foundState);
        stateServiceStub.retrieve.resolves([foundState]);

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
