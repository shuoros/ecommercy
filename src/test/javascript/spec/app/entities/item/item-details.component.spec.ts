/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ItemDetailComponent from '@/entities/item/item-details.vue';
import ItemClass from '@/entities/item/item-details.component';
import ItemService from '@/entities/item/item.service';
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
  describe('Item Management Detail Component', () => {
    let wrapper: Wrapper<ItemClass>;
    let comp: ItemClass;
    let itemServiceStub: SinonStubbedInstance<ItemService>;

    beforeEach(() => {
      itemServiceStub = sinon.createStubInstance<ItemService>(ItemService);

      wrapper = shallowMount<ItemClass>(ItemDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { itemService: () => itemServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundItem = { id: 123 };
        itemServiceStub.find.resolves(foundItem);

        // WHEN
        comp.retrieveItem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.item).toBe(foundItem);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundItem = { id: 123 };
        itemServiceStub.find.resolves(foundItem);

        // WHEN
        comp.beforeRouteEnter({ params: { itemId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.item).toBe(foundItem);
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
