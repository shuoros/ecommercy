/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CityUpdateComponent from '@/entities/city/city-update.vue';
import CityClass from '@/entities/city/city-update.component';
import CityService from '@/entities/city/city.service';

import StateService from '@/entities/state/state.service';
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
  describe('City Management Update Component', () => {
    let wrapper: Wrapper<CityClass>;
    let comp: CityClass;
    let cityServiceStub: SinonStubbedInstance<CityService>;

    beforeEach(() => {
      cityServiceStub = sinon.createStubInstance<CityService>(CityService);

      wrapper = shallowMount<CityClass>(CityUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          cityService: () => cityServiceStub,
          alertService: () => new AlertService(),

          stateService: () =>
            sinon.createStubInstance<StateService>(StateService, {
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
        comp.city = entity;
        cityServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cityServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.city = entity;
        cityServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCity = { id: 123 };
        cityServiceStub.find.resolves(foundCity);
        cityServiceStub.retrieve.resolves([foundCity]);

        // WHEN
        comp.beforeRouteEnter({ params: { cityId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.city).toBe(foundCity);
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
