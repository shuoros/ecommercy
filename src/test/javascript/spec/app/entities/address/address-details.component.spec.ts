/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AddressDetailComponent from '@/entities/address/address-details.vue';
import AddressClass from '@/entities/address/address-details.component';
import AddressService from '@/entities/address/address.service';
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
  describe('Address Management Detail Component', () => {
    let wrapper: Wrapper<AddressClass>;
    let comp: AddressClass;
    let addressServiceStub: SinonStubbedInstance<AddressService>;

    beforeEach(() => {
      addressServiceStub = sinon.createStubInstance<AddressService>(AddressService);

      wrapper = shallowMount<AddressClass>(AddressDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { addressService: () => addressServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAddress = { id: 123 };
        addressServiceStub.find.resolves(foundAddress);

        // WHEN
        comp.retrieveAddress(123);
        await comp.$nextTick();

        // THEN
        expect(comp.address).toBe(foundAddress);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAddress = { id: 123 };
        addressServiceStub.find.resolves(foundAddress);

        // WHEN
        comp.beforeRouteEnter({ params: { addressId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.address).toBe(foundAddress);
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
